package br.edu.ifpb.agora.service;

import java.util.Date;
import java.util.List;

import br.edu.ifpb.agora.repository.AlunoRepository;
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
    private AlunoRepository alunoRepository;
    
    @Autowired
    private ProcessoRepository processoRepository;

    @Transactional
    public void cadastraNovoProcesso(Processo processo){
        processo.setNumero("" + System.currentTimeMillis());
        Date dataRecepcao = new Date();

        Aluno aluno = alunoRepository.findById(1L).get();
        aluno.addProcesso(processo);

        processo.setStatus(StatusEnum.CRIADO);

        processo.setDataRecepcao(dataRecepcao);
        processoRepository.save(processo);
    }

    public List<Processo> consultaProcessos(){
        return processoRepository.findAllByInteressadoId(1L);
    }

    public List<Processo> consultaProcessosPorAssunto(String nome){
        return processoRepository.findAllByInteressadoIdAndAssuntoNome(1L, nome);
    }

    public List<Processo> consultaProcessosPorStatus(String status) {
        switch (status) {
            case "Criado":
                return processoRepository.findAllByInteressadoIdAndStatus(1L, StatusEnum.CRIADO);

            case "Distribuido":
                return processoRepository.findAllByInteressadoIdAndStatus(1L, StatusEnum.DISTRIBUIDO);

            case "Em pauta":
                return processoRepository.findAllByInteressadoIdAndStatus(1L, StatusEnum.EM_PAUTA);

            case "Em julgamento":
                return processoRepository.findAllByInteressadoIdAndStatus(1L, StatusEnum.EM_JULGAMENTO);

            default:
                return processoRepository.findAllByInteressadoIdAndStatus(1L, StatusEnum.JULGADO);

        }
    }

    public List<Processo> consultarProcessosPorAssuntoOrdenados(String assunto){
        return processoRepository.findAllByInteressadoIdAndAssuntoNomeOrderByDataRecepcaoDesc(1L, assunto);

    }

    public List<Processo> consultarProcessosPorStatusOrdenados(String status){
        switch (status) {
            case "Criado":
                return processoRepository.findAllByInteressadoIdAndStatusOrderByDataRecepcaoDesc(1L, StatusEnum.CRIADO);

            case "Distribuido":
                return processoRepository.findAllByInteressadoIdAndStatusOrderByDataRecepcaoDesc(1L, StatusEnum.DISTRIBUIDO);

            case "Em pauta":
                return processoRepository.findAllByInteressadoIdAndStatusOrderByDataRecepcaoDesc(1L, StatusEnum.EM_PAUTA);

            case "Em julgamento":
                return processoRepository.findAllByInteressadoIdAndStatusOrderByDataRecepcaoDesc(1L, StatusEnum.EM_JULGAMENTO);

            default:
                return processoRepository.findAllByInteressadoIdAndStatusOrderByDataRecepcaoDesc(1L, StatusEnum.JULGADO);

        }

    }

    public List<Processo> consultarProcessosOrdenados(Aluno aluno){
        return processoRepository.findAllByInteressadoIdOrderByDataRecepcaoDesc(1L);
    }

    @Transactional
    public void adicionarAnexo(Processo processo, byte[] anexo) {
        processo.addAnexos(anexo);
        processoRepository.save(processo);
    }
}
