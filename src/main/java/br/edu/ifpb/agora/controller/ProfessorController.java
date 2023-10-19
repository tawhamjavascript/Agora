package br.edu.ifpb.agora.controller;

import br.edu.ifpb.agora.model.Professor;
import br.edu.ifpb.agora.model.StatusReuniao;
import br.edu.ifpb.agora.service.AdminService;
import br.edu.ifpb.agora.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;


    @GetMapping("/login")
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("professor/login");
        mav.addObject("professor", new Professor());
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView login(Professor professor, ModelAndView mav) {
        if (professorService.login(professor)) {
            mav.setViewName("redirect:/professor/home");

        } else {
            mav.setViewName("professor/login");
            mav.addObject("professor", professor);
        }
        return mav;
    }

    @GetMapping("/home")
    public ModelAndView home(ModelAndView mav) {
        mav.setViewName("professor/home");
        return mav;
    }



    @GetMapping("/reunioes")
    public ModelAndView reuniões(ModelAndView mav) {
        mav.setViewName("professor/reunioes");
        mav.addObject("reunioes", professorService.listarReunioes());
        return mav;
    }

    @ModelAttribute("statusItens")
    public List<StatusReuniao> getStatus() {
        return List.of(StatusReuniao.values());
    }

    @PostMapping("/reunioes/filtro")
    public ModelAndView reuniõesByStatus(StatusReuniao status, ModelAndView mav) {
        mav.setViewName("redirect:/professor/reunioes");
        mav.addObject("reunioes", professorService.listarReunioesByStatus(status));
        return mav;
    }

    @GetMapping("/processos")
    public ModelAndView processos(ModelAndView mav) {
        mav.setViewName("professor/processos");
        mav.addObject("processos", professorService.listarProcessosDesignados());
        return mav;
    }

    @PostMapping("/processos")
    public ModelAndView processosVotar(Long id, String voto, String justificativa, ModelAndView mav) {
        professorService.votar(id, voto, justificativa);
        mav.setViewName("redirect:professor/processos");
        mav.addObject("processos", professorService.listarProcessosDesignados());
        return mav;
    }

}
