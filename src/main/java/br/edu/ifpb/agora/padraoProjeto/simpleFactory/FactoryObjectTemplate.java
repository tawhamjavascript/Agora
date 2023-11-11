package br.edu.ifpb.agora.padraoProjeto.simpleFactory;

import br.edu.ifpb.agora.padraoProjeto.templateMethod.AlunoTemplate;
import br.edu.ifpb.agora.padraoProjeto.templateMethod.ProfessorTemplate;
import br.edu.ifpb.agora.padraoProjeto.templateMethod.UserTemplate;
import br.edu.ifpb.agora.repository.AlunoRepository;
import br.edu.ifpb.agora.repository.ProfessorRepository;
import org.springframework.stereotype.Component;

@Component
public class FactoryObjectTemplate {


    public static UserTemplate create(String type, Object repository) {
        if(type.equals("professor")) {

            return new ProfessorTemplate((ProfessorRepository) repository);

        } else {

            return new AlunoTemplate((AlunoRepository) repository);

        }
    }


}
