package br.edu.ifpb.agora.padraoProjeto.templateMethod;

import br.edu.ifpb.agora.model.Aluno;
import br.edu.ifpb.agora.model.Usuario;
import br.edu.ifpb.agora.repository.AlunoRepository;
import org.springframework.stereotype.Component;

@Component
public class AlunoTemplate extends UserTemplate {


    private final AlunoRepository alunoRepository;

    public AlunoTemplate(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }





    public void verifyCoordenador(Usuario usuario) {
        // TODO Auto-generated method stub

    }
    public Usuario searchUsuario(Usuario usuario) {
        Aluno alunoBD = alunoRepository.findById(usuario.getId()).get();
        return alunoBD;
    }
    public void saveUsuario(Usuario usuario) {
        alunoRepository.save((Aluno) usuario);

    }
}
