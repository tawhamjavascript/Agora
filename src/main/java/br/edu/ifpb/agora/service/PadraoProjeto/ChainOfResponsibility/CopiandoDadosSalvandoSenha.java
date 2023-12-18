package br.edu.ifpb.agora.service.PadraoProjeto.ChainOfResponsibility;

import org.springframework.beans.BeanUtils;

import br.edu.ifpb.agora.model.Professor;
import br.edu.ifpb.agora.repository.AuthorityRepository;
import br.edu.ifpb.agora.repository.ProfessorRepository;
import br.edu.ifpb.agora.repository.UserRepository;
import jakarta.transaction.Transactional;

public class CopiandoDadosSalvandoSenha extends BaseHandler {

    public CopiandoDadosSalvandoSenha(ProfessorRepository professorRepository, AuthorityRepository authorityRepository,
            UserRepository userRepository, Professor professorBD, Professor professorForm) {
        super(professorRepository, authorityRepository, userRepository, professorBD, professorForm);
        //TODO Auto-generated constructor stub
    }

    @Override
    @Transactional
    public void handle() {
        BeanUtils.copyProperties(professorForm, professorBD, "senha", "processos", "matricula", "user", "colegiado", "votos");
        boolean matchPassword = hash.matches(professorForm.getSenha(), professorBD.getSenha());
        if (!matchPassword) {
            String passwordEncrypt = hash.encode(professorForm.getSenha());
            professorBD.setSenha(passwordEncrypt);
        }

        System.out.println("Entrando na cópia de propriedades");
        professorBD.getUser().getAuthorities().forEach((autority) -> System.out.println(autority));
        professorRepository.save(professorBD);


    }

    
}
