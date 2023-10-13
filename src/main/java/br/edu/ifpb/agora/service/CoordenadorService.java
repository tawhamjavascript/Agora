package br.edu.ifpb.agora.service;

import java.util.Date;
import java.util.List;

import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.repository.ColegiadoRepository;
import br.edu.ifpb.agora.repository.ReuniaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.agora.repository.ProcessoRepository;

@Service
public class CoordenadorService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ColegiadoRepository colegiadoRepository;

    @Autowired
    private ReuniaoRepository reuniaoRepository;


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
            return processoRepository.findAllByColegiadoAndInteressado(colegiado.getId(), interessado.getId());
        } else {
            throw new Error("Este professor não pode realizar esta consulta, pois não é coordenador!");
        }
    }

    public List<Processo> listarTodosProcessosDoColegiadoPorRelator(Professor coordenador, Colegiado colegiado, Professor relator){
        if (coordenador.isCoordenador()){
            return processoRepository.findAllByColegiadoAndRelator(colegiado.getId(), relator.getId());
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

    @Transactional
    public void iniciarSessao(Reuniao reuniao){

        Reuniao reuniao1 = reuniaoRepository.findByStatus(StatusReuniao.EM_ANDAMENTO);
        if(reuniao1 == null ) {
            reuniao.setStatus(StatusReuniao.EM_ANDAMENTO);
            reuniaoRepository.save(reuniao);
        }
        else {
            throw new RuntimeException("Já existe uma reunião em andamento!");
        }
    }
}
