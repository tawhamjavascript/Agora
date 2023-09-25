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
import org.springframework.stereotype.Service;

@Service
public class AlunoService {
    
    @Autowired
    private ProcessoRepository processoRepository;

    @Transactional
    public void cadastraNovoProcesso(Assunto assunto, String textoReq, Aluno aluno){
        String numProcesso = "" + System.currentTimeMillis();
        Date dataRecepcao = new Date();
        Processo processo = new Processo(numProcesso, dataRecepcao, null, null, null, null, assunto, aluno, textoReq);
        processoRepository.save(processo);
    }

    public List<Processo> consultaProcessos(Aluno aluno){
        return processoRepository.findAllByInteressadoId(aluno.getId());
    }

    public List<Processo> consultaProcessosPorAssunto(Aluno aluno, Assunto assunto){
        return processoRepository.findAllByInteressadoIdAndAssuntoNome(aluno.getId(), assunto.getNome());
    }

    public List<Processo> consultaProcessosPorStatus(Aluno aluno, StatusEnum status){
        return processoRepository.findAllByInteressadoIdAndStatus(aluno.getId(), status);
    }

    public List<Processo> consultarProcessosPorAssuntoOrdenados(Aluno aluno, Assunto assunto){
        return processoRepository.findAllByInteressadoIdAndAssuntoNomeOrderByDataRecepcaoDesc(aluno.getId(), assunto.getNome());

    }

    public List<Processo> consultarProcessosPorStatusOrdenados(Aluno aluno, StatusEnum status){
        return processoRepository.findAllByInteressadoIdAndStatusOrderByDataRecepcaoDesc(aluno.getId(), status);
    }

    public List<Processo> consultarProcessosOrdenados(Aluno aluno){
        return processoRepository.findAllByInteressadoIdOrderByDataRecepcaoDesc(aluno.getId());
    }

    @Transactional
    public void adicionarAnexo(Processo processo, byte[] anexo) {
        processo.addAnexos(anexo);
        processoRepository.save(processo);
    }
}
