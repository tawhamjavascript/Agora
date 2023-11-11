package br.edu.ifpb.agora.padraoProjeto.templateMethod;

import br.edu.ifpb.agora.model.Professor;
import br.edu.ifpb.agora.model.Usuario;
import br.edu.ifpb.agora.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfessorTemplate extends UserTemplate {

    private final ProfessorRepository professorRepository;

    public ProfessorTemplate(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }




    @Override
    public void verifyCoordenador(Usuario usuario) {
        Professor professor = (Professor) usuario;
        if (professor.isCoordenador()) {
            Professor atualCoordenador = professorRepository.findByCoordenadorTrueAndCursoId(professor.getCurso().getId());
            if (atualCoordenador != null) {
                atualCoordenador.setCoordenador(false);

            }
        }

    }

    @Override
    public Usuario searchUsuario(Usuario usuario) {
        Professor professorBD = professorRepository.findById(usuario.getId()).get();
        return professorBD;
    }

    @Override
    public void saveUsuario(Usuario usuario) {
        professorRepository.save((Professor) usuario);
    }
}
