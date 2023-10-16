package br.edu.ifpb.agora.service;


import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.repository.ProcessoRepository;
import br.edu.ifpb.agora.repository.ProfessorRepository;
import br.edu.ifpb.agora.repository.ReuniaoRepository;
import br.edu.ifpb.agora.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    public List<Processo> listarProcessosDesignados(Professor professor){
        return processoRepository.findAllByRelatorId(professor.getId());
    }

    public void votar(Voto voto) {
        votoRepository.save(voto);
    }

    public boolean login(Professor professor) {
        Professor professorBD = professorRepository.findByMatricula(professor.getMatricula());
        boolean valido = false;

        if(professorBD != null) {
            if (professor.getSenha().equals(professorBD.getSenha())) {
                valido = true;
            }
        }
        return valido;

    }

    public List<Reuniao> listarReunioes() {
        return reuniaoRepository.AllReunioesByProfessorAndColegiado(1L);

    }

    public List<Reuniao> listarReunioesByStatus(String status) {
        StatusReuniao statusReuniao = null;
        if(status.equals("encerrada")) {
            statusReuniao = StatusReuniao.ENCERRADA;
        }
        else if (status.equals("agendada")) {
            statusReuniao = StatusReuniao.PROGRAMADA;
        }
        else {
            statusReuniao = StatusReuniao.EM_ANDAMENTO;
        }
        return reuniaoRepository.AllReunioesByProfessorAndColegiadoAndStatus(1L, statusReuniao);
    }

    public List<Processo> listarProcessosDesignados() {
        return processoRepository.findAllByRelatorId(1L);
    }


    public void votar(Long id, String voto, String justificativa) {
        Processo processo = processoRepository.findById(id).get();
        TipoDecisao tipoDecisao = null;
        if (voto.equals("deferir")) {
            tipoDecisao = TipoDecisao.DEFERIDO;
        }
        else {
            tipoDecisao = TipoDecisao.INDEFERIDO;
        }

        processo.setTipoDecisao(tipoDecisao);
        processo.setTextoRelator(justificativa);
        processoRepository.save(processo);

    }
}
