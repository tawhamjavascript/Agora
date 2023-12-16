package br.edu.ifpb.agora.service;


import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.repository.ProcessoRepository;
import br.edu.ifpb.agora.repository.ProfessorRepository;
import br.edu.ifpb.agora.repository.ReuniaoRepository;
import br.edu.ifpb.agora.repository.VotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

    public List<Processo> listarProcessosDesignados(Principal user){

        return processoRepository.findAllByRelatorMatricula(user.getName());
    }

    @Transactional
    public void votar(Processo processo) {
        Processo processpBD = processoRepository.findById(processo.getId()).get();
        processpBD.setTipoDecisao(processo.getTipoDecisao());
        processpBD.setTextoRelator(processo.getTextoRelator());
        processoRepository.save(processpBD);

    }


    public List<Reuniao> listarReunioes(Principal user) {
        Professor professor = professorRepository.findByMatricula(user.getName());
        return reuniaoRepository.AllReunioesByProfessorAndColegiado(professor.getId());

    }

    public List<Reuniao> listarReunioesByStatus(Principal user, StatusReuniao status) {
        Professor professor = professorRepository.findByMatricula(user.getName());
        if (status.equals(StatusReuniao.SEM_FILTRO)) {
            return listarReunioes(user);
        }
        return reuniaoRepository.AllReunioesByProfessorAndColegiadoAndStatus(professor.getId(), status);
    }


    public Processo buscarProcesso(Long id) {
        return processoRepository.findById(id).get();
    }


    @Transactional
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
