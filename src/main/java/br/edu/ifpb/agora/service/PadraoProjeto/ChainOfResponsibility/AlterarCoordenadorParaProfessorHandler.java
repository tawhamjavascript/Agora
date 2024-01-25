package br.edu.ifpb.agora.service.PadraoProjeto.ChainOfResponsibility;

import java.util.List;

import br.edu.ifpb.agora.model.Authority;
import br.edu.ifpb.agora.model.Professor;
import br.edu.ifpb.agora.model.User;
import br.edu.ifpb.agora.repository.AuthorityRepository;
import br.edu.ifpb.agora.repository.ProfessorRepository;
import br.edu.ifpb.agora.repository.UserRepository;
import jakarta.transaction.Transactional;

public class AlterarCoordenadorParaProfessorHandler extends BaseHandler {

    public AlterarCoordenadorParaProfessorHandler(ProfessorRepository professorRepository,
            AuthorityRepository authorityRepository, UserRepository userRepository, Professor professorBD, Professor professorForm) {
        super(professorRepository, authorityRepository, userRepository, professorBD, professorForm);
        //TODO Auto-generated constructor stub
    }

    @Override
    @Transactional
    public void handle() {
        // TODO Auto-generated method stub
        User user = professorBD.getUser();
        List<Authority> authorities = user.getAuthorities();
        for(int i = 0; i < authorities.size(); i++) {
            if(authorities.get(i).getAuthority().equals("ROLE_COORDENADOR")) {
                System.out.println("retirando flag de coordenador");
                authorityRepository.delete(authorities.remove(i));
                userRepository.save(user);
            }
        }
    }    
}
