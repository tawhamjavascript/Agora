package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.edu.ifpb.agora.service.AuthService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public ModelAndView getForm(ModelAndView mav) {
        mav.setViewName("login/login");
        return mav;
    }

    @PostMapping
    public ModelAndView login(String matricula, String senha, String usuario, ModelAndView mav,
                              HttpSession session, RedirectAttributes redirectAttts) {

        Map<Usuario, String> usuarioBD = authService.login(matricula, senha, usuario);

        if (usuarioBD == null) {
            mav.setViewName("redirect:/auth");
            redirectAttts.addFlashAttribute("mensagem", "Usuário ou senha inválidos");

        } else {
            Usuario usuarioLogado = (Usuario) usuarioBD.keySet().toArray()[0];
            session.setAttribute("usuario", usuarioLogado);
            mav.setViewName("redirect:" + usuarioBD.get(usuarioLogado));

        }
        return mav;
    }
}
