package br.edu.ifpb.agora.service.PadraoProjeto.ChainOfResponsibility;

import br.edu.ifpb.agora.model.Authority;
import br.edu.ifpb.agora.model.Professor;
import br.edu.ifpb.agora.model.User;
import br.edu.ifpb.agora.repository.AuthorityRepository;
import br.edu.ifpb.agora.repository.ProfessorRepository;
import br.edu.ifpb.agora.repository.UserRepository;
import jakarta.transaction.Transactional;

public class AlterarProfessorParaCoordenadorHandler extends BaseHandler {

    public AlterarProfessorParaCoordenadorHandler(ProfessorRepository professorRepository,
            AuthorityRepository authorityRepository, UserRepository userRepository, Professor professorBD, Professor professorForm) {
        super(professorRepository, authorityRepository, userRepository,professorBD, professorForm);
        //TODO Auto-generated constructor stub
    }

    @Override
    @Transactional
    public void handle() {
        System.out.println("Entrando a permissão de professor para coordenador");
        User user = professorBD.getUser();
        System.out.println(user.getUsername());
        Authority authority = new Authority(user, "ROLE_COORDENADOR");
        user.getAuthorities().add(authority);
        user.getAuthorities().forEach((authorityInterator) -> System.out.println(authorityInterator));
    }   
}
