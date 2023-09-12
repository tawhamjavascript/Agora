package br.edu.ifpb.agora.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.agora.model.Aluno;
import br.edu.ifpb.agora.model.Colegiado;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.Professor;
import br.edu.ifpb.agora.model.StatusEnum;
import br.edu.ifpb.agora.repository.ProcessoRepository;

@Service
public class ProfessorService {

    @Autowired
    private ProcessoRepository processoRepository;

    public List<Processo> listarProcessosDesignados(Professor professor){
        return processoRepository.findAllByRelatorId(professor.getId());
    }


    public List<Processo> listarTodosProcessosDoColegiado(Professor coordenador, Colegiado colegiado){
        if (coordenador.isCoordenador()){
            return processoRepository.findAllByColegiado(colegiado.getId());
        } else {
            throw new Error("Este professor não pode realizar esta consulta, pois não é coordenador!");
        }
    }


    public List<Processo> listarTodosProcessosDoColegiadoPorStatus(Professor coordenador, Colegiado colegiado, StatusEnum status){
        if (coordenador.isCoordenador()){
            return processoRepository.findAllByColegiadoAndStatus(colegiado.getId(), status);
        } else {
            throw new Error("Este professor não pode realizar esta consulta, pois não é coordenador!");
        }
    }
    

    public List<Processo> listarTodosProcessosDoColegiadoPorAluno(Professor coordenador, Colegiado colegiado, Aluno interessado){
        if (coordenador.isCoordenador()){
            return processoRepository.findAllByColegiadoAndInteressado(colegiado.getId(), interessado);
        } else {
            throw new Error("Este professor não pode realizar esta consulta, pois não é coordenador!");
        }
    }

    public List<Processo> listarTodosProcessosDoColegiadoPorRelator(Professor coordenador, Colegiado colegiado, Professor relator){
        if (coordenador.isCoordenador()){
            return processoRepository.findAllByColegiadoAndRelator(colegiado.getId(), relator);
        } else {
            throw new Error("Este professor não pode realizar esta consulta, pois não é coordenador!");
        }
    }

    // é necessário verificar se o professor es
    @Transactional
    public void distribuirProcessoParaProfessorRelator(Professor coordenador, Professor relator, Processo processo){
        if (coordenador.isCoordenador()){
            if(processo.getRelator() == null) {
                // verificar se o prof
                processo.setRelator(relator);
            } else {
                throw new Error("Este processo já possui um relator!");
            }
            processoRepository.save(processo);
        } else {
            throw new Error("Este professor não pode realizar esta consulta, pois não é coordenador!");
        }
    }
}
