package br.edu.ifpb.agora.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.repository.*;
import br.edu.ifpb.agora.service.PadraoProjeto.DB4O;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoordenadorService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ColegiadoRepository colegiadoRepository;

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    private DB4O db4O = DB4O.getInstance();




    public List<Processo> listarTodosProcessosDoColegiado(Principal user) {

        Professor coordenador = professorRepository.findByMatricula(user.getName());
        return processoRepository.findAllByCursoId(coordenador.getCurso().getId());

    }

    public List<Professor> listarTodosProfessoresDoColegiado(Principal user) {

        Professor coordenador = professorRepository.findByMatricula(user.getName());
        Colegiado colegiado = colegiadoRepository.findByCursoId(coordenador.getCurso().getId());

        if (colegiado == null) {
            return null;
        }

        else {
            return colegiado.getMembros();
        }

    }

    public List<Aluno> listarTodosAlunosProcesso(Principal user) {
        Professor coordenador = professorRepository.findByMatricula(user.getName());
        return alunoRepository.findAllByAlunoAndProcessDistinct(coordenador.getCurso().getId());


    }

    public List<Processo> filtro(Principal user, String filtro){
        Long cursoId = professorRepository.findByMatricula(user.getName()).getCurso().getId();
        if (filtro.isBlank()) {
            return processoRepository.findAllByCursoId(cursoId);


        }
        try {
            Long usuarioId = Long.parseLong(filtro);

            Optional<Professor> professor = professorRepository.findById(usuarioId);

            if (professor.isPresent()) {
                return processoRepository.findAllByRelatorId(professor.get().getId());
            }
            else {
                return processoRepository.findAllByInteressadoId(usuarioId);
            }

        } catch (NumberFormatException e) {
            StatusEnum filtroEnum = StatusEnum.valueOf(filtro);
            return processoRepository.findAllByCursoIdAndStatus(cursoId, filtroEnum);

        }

    }

    public Processo getProcesso(Long idProcesso) {
        return processoRepository.findById(idProcesso).get();
    }

    @Transactional
    public void salvarProcesso(Processo processo) {
        Processo processoBD = processoRepository.findById(processo.getId()).get();
        processoBD.setRelator(processo.getRelator());
        processoBD.setStatus(StatusEnum.DISTRIBUIDO);
        processoBD.setDataDistribuicao(new Date());
        processoRepository.save(processoBD);
    }


    public List<Processo> getProcessosDoCursoEComDecisaoRelator(Principal principal) {
        Professor professor = professorRepository.findByMatricula(principal.getName());
        return processoRepository.findAllByStatusAndCursoIdAndDecisaoRelatorIsNotNull(StatusEnum.DISTRIBUIDO, professor.getCurso().getId());
        
    }

    public List<Reuniao> getReuniaoDoColegiado(Principal user) {
        Professor professor = professorRepository.findByMatricula(user.getName());
        Colegiado colegiado = colegiadoRepository.findByCursoId(professor.getCurso().getId());
        List<Reuniao> reunioes = reuniaoRepository.findByColegiadoId(colegiado.getId());
        return reunioes;
    }

    @Transactional
    public void salvarReuniao(Principal user, Reuniao reuniao) {
        Reuniao reuniaoMemoria = db4O.deletar(user.getName());
        reuniao.setStatus(StatusReuniao.PROGRAMADA);

        Professor professor = professorRepository.findByMatricula(user.getName());
        Colegiado colegiado = colegiadoRepository.findByCursoId(professor.getCurso().getId());

        colegiado.addReuniao(reuniao);
        reuniao.setColegiado(colegiado);
        reuniao.setDataReuniao(reuniaoMemoria.getDataReuniao());

        List<Processo> processosPersistidos = new ArrayList<>();

        reuniaoMemoria.getProcessos().forEach(processo -> {
            Processo processoBD = processoRepository.findById(processo.getId()).get();
            processosPersistidos.add(processoBD);
        });
        reuniao.setProcessos(processosPersistidos);

        

        colegiadoRepository.save(colegiado);
        reuniaoRepository.save(reuniao);
    }

    public void salvarProcessoEmMemoria(Principal user, Long idProcesso) {
        Processo processo = processoRepository.findById(idProcesso).get();
        processo.setStatus(StatusEnum.EM_PAUTA);
        processoRepository.save(processo);
        db4O.addProcesso(user.getName(), processo);
  
    }

    public List<Processo> getProcessosEmMemoria(Principal user) {
        return db4O.getReunioesProcesso(user.getName());  


        
    }

    public Reuniao getReuniaoCadastro(Principal user) {
        if (!db4O.existeReuniao(user.getName())) {
            System.out.println("usuário não existe");
            return new Reuniao();

        } 
        System.out.println(db4O.getReuniao(user.getName()).getHorario()
        + db4O.getReuniao(user.getName()).getDataReuniao());
        return db4O.getReuniao(user.getName());
    }

    public void salvarReuniaoMemoria(Principal user, Reuniao reuniao) {
        this.db4O.addReuniao(user.getName(), reuniao);


    }

//    public List<Processo> listarTodosProcessosDoColegiadoPorStatus(Professor coordenador, Colegiado colegiado, StatusEnum status){
//        if (coordenador.isCoordenador()){
//            return processoRepository.findAllByColegiadoAndStatus(colegiado.getId(), status);
//        } else {
//            throw new Error("Este professor não pode realizar esta consulta, pois não é coordenador!");
//        }
//    }
    

//    public List<Processo> listarTodosProcessosDoColegiadoPorAluno(Professor coordenador, Colegiado colegiado, Aluno interessado){
//        if (coordenador.isCoordenador()){
//            return processoRepository.findAllByColegiadoAndInteressado(colegiado.getId(), interessado.getId());
//        } else {
//            throw new Error("Este professor não pode realizar esta consulta, pois não é coordenador!");
//        }
//    }

//    public List<Processo> listarTodosProcessosDoColegiadoPorRelator(Professor coordenador, Colegiado colegiado, Professor relator){
//        if (coordenador.isCoordenador()){
//            return processoRepository.findAllByColegiadoAndRelator(colegiado.getId(), relator.getId());
//        } else {
//            throw new Error("Este professor não pode realizar esta consulta, pois não é coordenador!");
//        }
//    }

    // é necessário verificar se o professor es
//    @Transactional
//    public void distribuirProcessoParaProfessorRelator(Professor coordenador, Professor relator, Processo processo){
//        if (coordenador.isCoordenador()){
//            if(processo.getRelator() == null) {
//                // verificar se o prof
//                processo.setRelator(relator);
//            } else {
//                throw new Error("Este processo já possui um relator!");
//            }
//            processoRepository.save(processo);
//        } else {
//            throw new Error("Este professor não pode realizar esta consulta, pois não é coordenador!");
//        }
//    }

//    @Transactional
//    public void criarReuniao(List<Processo> processos, Date data, Colegiado colegiado) {
//        Reuniao reuniao = new Reuniao(data, StatusReuniao.PROGRAMADA, colegiado);
//
//        for (Processo processo : processos) {
//            reuniao.addProcesso(processo);
//        }
//
//        colegiado.addReuniao(reuniao);
//
//        colegiadoRepository.save(colegiado);
//
//        reuniaoRepository.save(reuniao);
//
//
//    }

//    @Transactional
//    public void iniciarSessao(Reuniao reuniao){
//
//        Reuniao reuniao1 = reuniaoRepository.findByStatus(StatusReuniao.EM_ANDAMENTO);
//        if(reuniao1 == null ) {
//            reuniao.setStatus(StatusReuniao.EM_ANDAMENTO);
//            reuniaoRepository.save(reuniao);
//        }
//        else {
//            throw new RuntimeException("Já existe uma reunião em andamento!");
//        }
//    }
}
