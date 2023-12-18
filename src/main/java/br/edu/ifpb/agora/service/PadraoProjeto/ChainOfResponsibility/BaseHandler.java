package br.edu.ifpb.agora.service.PadraoProjeto.ChainOfResponsibility;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.edu.ifpb.agora.model.Professor;
import br.edu.ifpb.agora.repository.AuthorityRepository;
import br.edu.ifpb.agora.repository.ProfessorRepository;
import br.edu.ifpb.agora.repository.UserRepository;

public abstract class BaseHandler implements Handler {
    protected PasswordEncoder hash = new BCryptPasswordEncoder();

    protected BaseHandler handler;
    
    protected ProfessorRepository professorRepository;

    protected AuthorityRepository authorityRepository;

    protected UserRepository userRepository;

    protected Professor professorBD;

    protected Professor professorForm;

    public BaseHandler(ProfessorRepository professorRepository, 
                       AuthorityRepository authorityRepository,
                       UserRepository userRepository,
                       Professor professorBD,
                       Professor professorForm
                      ) {

        this.professorRepository = professorRepository;
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.professorBD = professorBD;
        this.professorForm = professorForm;
        
    }

    public void setNextHandler(BaseHandler handler) {
        this.handler = handler;
    }

    public BaseHandler getNextHandler() {
        return handler;
    }
    
}
