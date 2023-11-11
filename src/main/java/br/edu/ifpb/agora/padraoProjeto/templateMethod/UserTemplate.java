package br.edu.ifpb.agora.padraoProjeto.templateMethod;

import br.edu.ifpb.agora.model.Professor;
import br.edu.ifpb.agora.model.Usuario;
import br.edu.ifpb.agora.util.PasswordUtil;
import org.springframework.stereotype.Component;

@Component
public abstract class UserTemplate {

    public void hashPassword(Usuario usuario) {

        verifyCoordenador(usuario);

        if(usuario.getId() != null) {
            Usuario professorBD = searchUsuario(usuario);

            if (PasswordUtil.checkPass(usuario.getSenha(), professorBD.getSenha())) {
                usuario.setSenha(professorBD.getSenha());

            } else {
                usuario.setSenha(PasswordUtil.hashPassword(usuario.getSenha()));
            }

        } else {
            usuario.setSenha(PasswordUtil.hashPassword(usuario.getSenha()));
        }

        saveUsuario(usuario);
    }
    public abstract void verifyCoordenador (Usuario usuario);

    public abstract Usuario searchUsuario(Usuario usuario);

    public abstract void saveUsuario(Usuario usuario);
}
