package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public ModelAndView getForm(ModelAndView mav) {
        mav.setViewName("login/login");
        return mav;
    }
}
