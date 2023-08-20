package br.edu.ifpb.agora.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ifpb.agora.model.Aluno;
import br.edu.ifpb.agora.model.Assunto;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.StatusEnum;
import br.edu.ifpb.agora.repository.ProcessoRepository;
import jakarta.transaction.Transactional;

public class AlunoService {
    
    @Autowired
    private ProcessoRepository processoRepository;

    @Transactional
    public void cadastraNovoProcesso(Assunto assunto, String textoReq, Aluno aluno){
        
        String numProcesso = "" + System.currentTimeMillis();
        Date dataRecepcao = new Date();
        Processo processo = new Processo(numProcesso, dataRecepcao, null, null, null, null, assunto, aluno, null);
        processoRepository.save(processo);
    }

    @Transactional
    public List<Processo> consultaProcessos(Aluno aluno){
        return processoRepository.findAllByInteressado(aluno);
    }

    @Transactional
    public List<Processo> consultaProcessosPorAssunto(Aluno aluno, Assunto assunto){
        return processoRepository.findAllByAlunoAndAssunto(aluno, assunto);
    }

    @Transactional
    public List<Processo> consultaProcessosPorStatus(Aluno aluno, StatusEnum status){
        return processoRepository.findAllByAlunoAndStatus(aluno, status);
    }
}
