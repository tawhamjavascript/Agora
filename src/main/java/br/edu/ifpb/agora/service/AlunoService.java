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

        Aluno aluno = alunoRepository.findById(6L).get();
        aluno.addProcesso(processo);

        processo.setStatus(StatusEnum.CRIADO);

        processo.setTipoDecisao(null);

        processo.setInteressado(aluno);

        processo.setCurso(aluno.getCurso());

        processo.setDataRecepcao(dataRecepcao);
        processoRepository.save(processo);
    }

    public List<Processo> consultaProcessos(){
        return processoRepository.findAllByInteressadoId(6L);
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
        return processoRepository.findAllByInteressadoIdAndAssuntoId(6L, id);
    }

    public List<Processo> consultaProcessosPorStatus(StatusEnum status) {
        return processoRepository.findAllByInteressadoIdAndStatus(6L, status);

    }

    public List<Processo> consultarProcessosPorAssuntoOrdenados(Long id){
        return processoRepository.findAllByInteressadoIdAndAssuntoIdOrderByDataRecepcaoDesc(6L, id);

    }

    public List<Processo> consultarProcessosPorStatusOrdenados(StatusEnum status){
        return processoRepository.findAllByInteressadoIdAndStatusOrderByDataRecepcaoDesc(6L, status);



    }

    public List<Processo> consultarProcessosOrdenados(Aluno aluno){
        return processoRepository.findAllByInteressadoIdOrderByDataRecepcaoDesc(6L);
    }

    @Transactional
    public void adicionarAnexo(Processo processo, byte[] anexo) {
        processo.addAnexos(anexo);
        processoRepository.save(processo);
    }
}
