package br.edu.ifpb.agora.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.repository.*;
import br.edu.ifpb.agora.service.PadraoProjeto.DB4O;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Reuniao> getReuniaoDoColegiado(Principal user, Pageable paging) {
        Professor professor = professorRepository.findByMatricula(user.getName());
        Colegiado colegiado = colegiadoRepository.findByCursoId(professor.getCurso().getId());
        Page<Reuniao> reunioes = reuniaoRepository.findByColegiadoId(colegiado.getId(), paging);
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

    @Transactional
    private void iniciarReuniao(Reuniao reuniao) {
        Reuniao reuniaoBD = reuniaoRepository.findById(reuniao.getId()).get();
        reuniao.setStatus(StatusReuniao.EM_ANDAMENTO);
        for (Processo processo : reuniaoBD.getProcessos()) {
            if (processo.getStatus() == StatusEnum.EM_PAUTA) {
                processo.setStatus(StatusEnum.EM_JULGAMENTO);
                processoRepository.save(processo);
                break;
            }
        }

        reuniaoRepository.save(reuniaoBD);
    }

    public boolean existeReuniaoEmAndamento(Long id, Principal user) {
        Professor professor = professorRepository.findByMatricula(user.getName());
        Colegiado colegiado = colegiadoRepository.findByCursoId(professor.getCurso().getId());
        List<Reuniao> reunioes = colegiado.getReunioes();
        for (Reuniao reuniao : reunioes) {
            if (reuniao.getStatus() == StatusReuniao.EM_ANDAMENTO && reuniao.getId() != id) {
                System.out.println("aqui");
                return true;
            }
        }
        return false;
    }

    @Transactional
    public Reuniao getReuniao(Long idReuniao) {
        Reuniao reuniao = reuniaoRepository.findById(idReuniao).get();
        if (reuniao.getStatus() == StatusReuniao.PROGRAMADA) {
            iniciarReuniao(reuniao);
            
        }
        reuniaoRepository.save(reuniao);

        return reuniao;
    }

    public Map<String, Voto> getVotos(Reuniao reuniao) {
        Processo processo = new Processo();
        for (Processo p : reuniao.getProcessos()) {
            if (p.getStatus() == StatusEnum.EM_JULGAMENTO) {
                processo = p;

            }
        }
        if (processo.getId() == null) {
            return null;
        }
        Map<String, Voto> votos = new HashMap<>();
        Colegiado colegiado = reuniao.getColegiado();
        for(Professor professor: colegiado.getMembros()) {
            for (Voto voto : processo.getVotos()) {
                if (voto.getProfessor().getId() == professor.getId()) {
                    votos.put(professor.getNome(), voto);
                    break;
                }
                else {
                    votos.put(professor.getNome(), null);
                }
            }
        }

        if (votos.isEmpty()) {
            System.out.println("Mapa vazio");
        } else {
            votos.forEach((key, voto) -> {
                if (voto == null) {
                    System.out.println("Key: " + key + " Voto: null");
                }
                else {
                    System.out.println("Key: " + key + " Voto: " + voto.getTipoVoto());
                }
            });
        }
    
        return votos;
    }

    @Transactional
    public void salvarVoto(Long idReuniao, Long idProfessor, String tipoVoto) {
        Reuniao reuniao = reuniaoRepository.findById(idReuniao).get();
        Professor professor = professorRepository.findById(idProfessor).get();
        Processo processo = new Processo();
        for (Processo p : reuniao.getProcessos()) {
            if (p.getStatus() == StatusEnum.EM_JULGAMENTO) {
                processo = p;

            }
        }
        Voto voto = new Voto();
        voto.setProfessor(professor);
        voto.setTipoVoto(TipoVoto.valueOf(tipoVoto));
        processo.addVoto(voto);
        if(processo.getVotos().size() == reuniao.getColegiado().getMembros().size()) {
            decisaoColegiado(processo);
        }
        processoRepository.save(processo);
        
        
    }

    @Transactional
    private void decisaoColegiado(Processo processo) {
        int divergenteDoRelator = 0;
        int comRelator = 0;
        for (Voto voto : processo.getVotos()) {
            if (voto.getTipoVoto() == TipoVoto.DIVERGENTE) {
                divergenteDoRelator++;
            }
            else {
                comRelator++;
            }
        }

        if (divergenteDoRelator > comRelator) {
            if (processo.getDecisaoRelator() == TipoDecisao.DEFERIDO) {
                processo.setDecisaoColegiado(TipoDecisao.INDEFERIDO);
                
            } else {
                processo.setDecisaoColegiado(TipoDecisao.DEFERIDO);
                
            }
        }
        else {
            processo.setDecisaoColegiado(processo.getDecisaoRelator());
        }
        processoRepository.save(processo);
    }

    @Transactional
    public void finalizarVotacao(Long idReuniao) {
        Reuniao reuniao = reuniaoRepository.findById(idReuniao).get();
        Processo processo = new Processo();
        for (Processo p : reuniao.getProcessos()) {
            if (p.getStatus() == StatusEnum.EM_JULGAMENTO) {
                processo = p;
            }
        }
        processo.setStatus(StatusEnum.JULGADO);
        Processo processoSeguinte = null;

        for(Processo p : reuniao.getProcessos()) {
            if (p.getStatus() == StatusEnum.EM_PAUTA) {
                processoSeguinte = p;
            }
        }
        if (processoSeguinte != null) {
            processoSeguinte.setStatus(StatusEnum.EM_JULGAMENTO);
            processoRepository.save(processoSeguinte);
        }
        processoRepository.save(processo);
    
    }
    

    @Transactional
    public boolean finalizarReuniao(Long idSessao) {
        int contadorDeProcessosVotados =  0;

        Reuniao reuniao = reuniaoRepository.findById(idSessao).get();

        for(Processo processo: reuniao.getProcessos()) {
            if (processo.getDecisaoColegiado() != null) {
                contadorDeProcessosVotados++;
            }
        }

        if (contadorDeProcessosVotados == reuniao.getProcessos().size() && reuniao.getAta() != null) {
            reuniao.setStatus(StatusReuniao.ENCERRADA);
            reuniaoRepository.save(reuniao);
            return true;
        } 
        return false;
    }



    public Page<Processo> listarTodosProcessosDoColegiado(Long id, Pageable paging) {
        Professor coordenador = professorRepository.findById(id).get();
        return processoRepository.findAllByCursoId(coordenador.getCurso().getId(), paging);
    }
}
