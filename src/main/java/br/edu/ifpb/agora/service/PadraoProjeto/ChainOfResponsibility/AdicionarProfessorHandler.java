package br.edu.ifpb.agora.service.PadraoProjeto.ChainOfResponsibility;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.agora.model.Authority;
import br.edu.ifpb.agora.model.Professor;
import br.edu.ifpb.agora.model.User;
import br.edu.ifpb.agora.repository.AuthorityRepository;
import br.edu.ifpb.agora.repository.ProfessorRepository;
import br.edu.ifpb.agora.repository.UserRepository;
import jakarta.transaction.Transactional;

public class AdicionarProfessorHandler extends BaseHandler{

    public AdicionarProfessorHandler(ProfessorRepository professorRepository, AuthorityRepository authorityRepository,
            UserRepository userRepository, Professor professorBD, Professor professorForm) {
        super(professorRepository, authorityRepository, userRepository, professorBD, professorForm);
        //TODO Auto-generated constructor stub
    }

    @Override
    @Transactional
    public void handle() {
        // TODO Auto-generated method stub

        String passwordEncrypt = hash.encode((CharSequence) professorForm.getSenha());
        User user = new User(professorForm.getMatricula(), passwordEncrypt);
        user.setEnabled(true);

        List<Authority> authorities = new ArrayList<Authority>(2);
        authorities.add(new Authority(user, "ROLE_PROFESSOR"));
        if (professorForm.isCoordenador()) {
            authorities.add(new Authority(user, "ROLE_COORDENADOR"));
        }
        user.setAuthorities(authorities);
        professorForm.setUser(user);
        professorRepository.save(professorForm);

    }
    
}
