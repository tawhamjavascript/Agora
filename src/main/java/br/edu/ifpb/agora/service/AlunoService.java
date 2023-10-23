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
    public void cadastraNovoProcesso(Long id, Processo processo){
        processo.setNumero("" + System.currentTimeMillis());
        Date dataRecepcao = new Date();

        Aluno aluno = alunoRepository.findById(id).get();
        aluno.addProcesso(processo);

        processo.setStatus(StatusEnum.CRIADO);

        processo.setTipoDecisao(null);

        processo.setInteressado(aluno);

        processo.setCurso(aluno.getCurso());

        processo.setDataRecepcao(dataRecepcao);
        processoRepository.save(processo);
    }

    public List<Processo> consultaProcessos(Long id){
        return processoRepository.findAllByInteressadoId(id);
    }


    public List<Processo> filtrarProcesso(Long id, String filtro, String order) {
        try {
            Long assuntoId = Long.parseLong(filtro);
            if (order.isBlank()) {
                return consultaProcessosPorAssunto(id, assuntoId);
            }
            else {
                return consultarProcessosPorAssuntoOrdenados(id, assuntoId);
            }

        } catch (NumberFormatException e) {
            StatusEnum filtroEnum = StatusEnum.valueOf(filtro);
            if (order.isBlank()) {
                return consultaProcessosPorStatus(id, filtroEnum);
            }
            else {
                return consultarProcessosPorStatusOrdenados(id, filtroEnum);
            }
        }
    }

    public List<Processo> consultaProcessosPorAssunto(Long idAluno, Long id){
        return processoRepository.findAllByInteressadoIdAndAssuntoId(idAluno, id);
    }

    public List<Processo> consultaProcessosPorStatus(Long id, StatusEnum status) {
        return processoRepository.findAllByInteressadoIdAndStatus(id, status);

    }

    public List<Processo> consultarProcessosPorAssuntoOrdenados(Long idAluno, Long id){
        return processoRepository.findAllByInteressadoIdAndAssuntoIdOrderByDataRecepcaoDesc(idAluno, id);

    }

    public List<Processo> consultarProcessosPorStatusOrdenados(Long id, StatusEnum status){
        return processoRepository.findAllByInteressadoIdAndStatusOrderByDataRecepcaoDesc(id, status);



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
