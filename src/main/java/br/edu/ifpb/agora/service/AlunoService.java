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


    public List<Processo> filtrarProcesso(String filtro, String order) {
        try {
            Long assuntoId = Long.parseLong(filtro);
            if (order == null) {
                return consultaProcessosPorAssunto(assuntoId);
            }
            else {
                return consultarProcessosPorAssuntoOrdenados(assuntoId);
            }

        } catch (NumberFormatException e) {
            StatusEnum filtroEnum = StatusEnum.valueOf(filtro);
            if (order == null) {
                return consultaProcessosPorStatus(filtroEnum);
            }
            else {
                return consultarProcessosPorStatusOrdenados(filtroEnum);
            }
        }
    }

    public List<Processo> consultaProcessosPorAssunto(Long id){
        return processoRepository.findAllByInteressadoIdAndAssuntoId(1L, id);
    }

    public List<Processo> consultaProcessosPorStatus(StatusEnum status) {
        return processoRepository.findAllByInteressadoIdAndStatus(1L, status);

    }

    public List<Processo> consultarProcessosPorAssuntoOrdenados(Long id){
        return processoRepository.findAllByInteressadoIdAndAssuntoIdOrderByDataRecepcaoDesc(1L, id);

    }

    public List<Processo> consultarProcessosPorStatusOrdenados(StatusEnum status){
        return processoRepository.findAllByInteressadoIdAndStatusOrderByDataRecepcaoDesc(1L, status);



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
