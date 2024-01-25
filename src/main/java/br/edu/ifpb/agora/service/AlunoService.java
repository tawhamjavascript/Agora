package br.edu.ifpb.agora.service;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import br.edu.ifpb.agora.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifpb.agora.model.Aluno;
import br.edu.ifpb.agora.model.Assunto;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.StatusEnum;
import br.edu.ifpb.agora.repository.ProcessoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;
    
    @Autowired
    private ProcessoRepository processoRepository;

    @Transactional
    public void cadastraNovoProcesso(Principal user, Processo processo){
        processo.setNumero("" + System.currentTimeMillis());
        Date dataRecepcao = new Date();
     

        Aluno aluno = alunoRepository.findByMatricula(user.getName());
        aluno.addProcesso(processo);

        processo.setStatus(StatusEnum.CRIADO);

        processo.setTipoDecisao(null);

        processo.setInteressado(aluno);

        processo.setCurso(aluno.getCurso());

        processo.setDataRecepcao(dataRecepcao);
        processoRepository.save(processo);
    }


    public List<Processo> consultaProcessos(Principal user){
        return processoRepository.findAllByInteressadoMatricula(user.getName());
    }

     public Page<Processo> consultaProcessos(Principal user, Pageable paging){
        return processoRepository.findAllByInteressadoMatricula(user.getName(), paging);
    }


    public List<Processo> filtrarProcesso(Principal user, String filtro, String order) {
        if (filtro.isBlank()) {
            return consultaProcessos(user);

        }
        try {
            Long assuntoId = Long.parseLong(filtro);
            if (order.isBlank()) {
                return consultaProcessosPorAssunto(user.getName(), assuntoId);
            }
            else {
                return consultarProcessosPorAssuntoOrdenados(user.getName(), assuntoId);
            }

        } catch (NumberFormatException e) {
            StatusEnum filtroEnum = StatusEnum.valueOf(filtro);
            if (order.isBlank()) {
                return consultaProcessosPorStatus(user.getName(), filtroEnum);
            }
            else {
                return consultarProcessosPorStatusOrdenados(user.getName(), filtroEnum);
            }
        }
    }

    public List<Processo> consultaProcessosPorAssunto(String matricula, Long id){
        return processoRepository.findAllByInteressadoMatriculaAndAssuntoId(matricula, id);
    }

    public List<Processo> consultaProcessosPorStatus(String matricula, StatusEnum status) {
        return processoRepository.findAllByInteressadoMatriculaAndStatus(matricula, status);

    }

    public List<Processo> consultarProcessosPorAssuntoOrdenados(String matricula, Long id){
        return processoRepository.findAllByInteressadoMatriculaAndAssuntoIdOrderByDataRecepcaoDesc(matricula, id);

    }

    public List<Processo> consultarProcessosPorStatusOrdenados(String matricula, StatusEnum status){
        return processoRepository.findAllByInteressadoMatriculaAndStatusOrderByDataRecepcaoDesc(matricula, status);

    }

    public List<Processo> consultarProcessosOrdenados(String matricula){
        return processoRepository.findAllByInteressadoMatriculaOrderByDataRecepcaoDesc(matricula);
    }

}
