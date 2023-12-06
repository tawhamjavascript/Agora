package br.edu.ifpb.agora.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.agora.service.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getHome(Principal principal, ModelAndView mav) {
        mav.setViewName("redirect:" + this.userService.userHome(principal));
        return mav;
    }


    
}
