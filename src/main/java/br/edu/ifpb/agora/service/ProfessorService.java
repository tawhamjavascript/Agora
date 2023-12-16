package br.edu.ifpb.agora.service;


import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.repository.ProcessoRepository;
import br.edu.ifpb.agora.repository.ProfessorRepository;
import br.edu.ifpb.agora.repository.ReuniaoRepository;
import br.edu.ifpb.agora.repository.VotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<Processo> listarProcessosDesignados(Usuario professor){
        return processoRepository.findAllByRelatorId(professor.getId());
    }

    public Page<Processo> listarProcessosDesignados(Usuario professor, Pageable paging) {
        return processoRepository.findAllByRelatorId(professor.getId(), paging);
    }

    @Transactional
    public void votar(Processo processo) {
        Processo processpBD = processoRepository.findById(processo.getId()).get();
        processpBD.setTipoDecisao(processo.getTipoDecisao());
        processpBD.setTextoRelator(processo.getTextoRelator());
        processoRepository.save(processpBD);

    }


    public List<Reuniao> listarReunioes(Usuario professor) {
        return reuniaoRepository.AllReunioesByProfessorAndColegiado(professor.getId());

    }

    public List<Reuniao> listarReunioesByStatus(Usuario professor, StatusReuniao status) {
        if (status.equals(StatusReuniao.SEM_FILTRO)) {
            return listarReunioes(professor);
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

    public Page<Reuniao> listarReunioes(Usuario professor, Pageable paging) {
        return reuniaoRepository.AllReunioesByProfessorAndColegiado(professor.getId(), paging);
    }

    
}
