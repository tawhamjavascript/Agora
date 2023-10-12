package br.edu.ifpb.agora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.agora.service.AdminService;

@Controller
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private AdminService adminService;


    
}
