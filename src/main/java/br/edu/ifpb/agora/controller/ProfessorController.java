package br.edu.ifpb.agora.controller;

import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.service.AdminService;
import br.edu.ifpb.agora.service.ProfessorService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import java.util.HashMap;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;


    // private HashMap<String, String> pathTo = new HashMap<String, String>();    

    // private List<String> getPath(String page) {
    // if(page.equals("cadastro")) {
    //         return Arrays.asList("/css/aluno/main.css", "/css/aluno/cadastro-processo-aluno.css");
    //     } else if (page.equals("listagem")) {
    //         return Arrays.asList("/css/aluno/main.css", "/css/aluno/aluno.css");
    //     }
    //     return Arrays.asList("/css/aluno/main.css");
    // }




    @GetMapping("/home")
    public ModelAndView home(ModelAndView mav) {
        mav.setViewName("professor/home");
        return mav;
    }



    @GetMapping("/reunioes")
    public ModelAndView reunioes(ModelAndView mav, Principal user) {
        mav.setViewName("professor/reunioes");
        mav.addObject("reunioes", professorService.listarReunioes(user));
        return mav;
    }

    @ModelAttribute("statusItens")
    public List<StatusReuniao> getStatus() {
        return List.of(StatusReuniao.values());
    }

    @ModelAttribute("decisaoItens")
    public List<TipoDecisao> getDecisao() {
        return List.of(TipoDecisao.values());
    }

    @GetMapping("/reunioes/filtro")
    public ModelAndView reunioesByStatus(@RequestParam(name = "filtro") StatusReuniao filtro, Principal user, ModelAndView mav) {
        mav.setViewName("professor/reunioes");

        mav.addObject("reunioes", professorService.listarReunioesByStatus(user, filtro));
        return mav;
    }

    @GetMapping("/processos")
    public ModelAndView processos(ModelAndView mav, Principal principal) {

        mav.setViewName("professor/processos");
        mav.addObject("processos", professorService.listarProcessosDesignados(principal));
        return mav;
    }

    @GetMapping("/processo/{id}")
    public ModelAndView processo(@PathVariable("id") Long id, ModelAndView mav) {
        Processo processo = professorService.buscarProcesso(id);
        mav.setViewName("professor/votoRelator");
        mav.addObject("processo", processo);
        return mav;
    }

    @PostMapping("/processo")
    public ModelAndView processoVotar(Processo processo,ModelAndView mav) {
        professorService.votar(processo);
        mav.setViewName("redirect:/professor/processos");
        return mav;
    }



//    @PostMapping("/processos")
//    public ModelAndView processosVotar(Long id, String voto, String justificativa, ModelAndView mav) {
//        professorService.votar(id, voto, justificativa);
//        mav.setViewName("redirect:professor/processos");
//        mav.addObject("processos", professorService.listarProcessosDesignados());
//        return mav;
//    }

}
