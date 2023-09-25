package br.edu.ifpb.agora.service;


import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.Professor;
import br.edu.ifpb.agora.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProcessoRepository processoRepository;

    public List<Processo> listarProcessosDesignados(Professor professor){
        return processoRepository.findAllByRelatorId(professor.getId());
    }

}
