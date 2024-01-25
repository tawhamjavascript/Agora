package br.edu.ifpb.agora.service.PadraoProjeto.ChainOfResponsibility;

import java.util.List;

import br.edu.ifpb.agora.model.Authority;
import br.edu.ifpb.agora.model.Professor;
import br.edu.ifpb.agora.repository.AuthorityRepository;
import br.edu.ifpb.agora.repository.ProfessorRepository;
import br.edu.ifpb.agora.repository.UserRepository;
import jakarta.transaction.Transactional;

public class AtualizarCoordenadorHandler extends BaseHandler{

 
    public AtualizarCoordenadorHandler(ProfessorRepository professorRepository, AuthorityRepository authorityRepository,
            UserRepository userRepository, Professor professorBD, Professor professorForm) {
        super(professorRepository, authorityRepository, userRepository, professorBD, professorForm);
        //TODO Auto-generated constructor stub
    }

    @Transactional
    public void handle() {
        professorBD.setCoordenador(false);
        List<Authority> authorities = professorBD.getUser().getAuthorities();
        for(int i = 0; i < authorities.size(); i++) {
            if(authorities.get(i).getAuthority().equals("ROLE_COORDENADOR")) {
                authorityRepository.delete(authorities.remove(i));
                userRepository.save(professorBD.getUser());
                professorRepository.save(professorBD);
            }
        }
    }
}
