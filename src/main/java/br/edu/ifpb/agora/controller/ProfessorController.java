package br.edu.ifpb.agora.controller;

import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.service.AdminService;
import br.edu.ifpb.agora.service.ProfessorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;


    @GetMapping("/home")
    public ModelAndView home(ModelAndView mav) {
        mav.setViewName("professor/home");
        return mav;
    }



    @GetMapping("/reunioes")
    public ModelAndView reunioes(ModelAndView mav, HttpSession session) {
        Usuario professor = (Usuario) session.getAttribute("usuario");
        mav.setViewName("professor/reunioes");
        mav.addObject("reunioes", professorService.listarReunioes(professor));
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
    public ModelAndView reunioesByStatus(@RequestParam(name = "filtro") StatusReuniao filtro, HttpSession session, ModelAndView mav) {
        Usuario professor = (Usuario) session.getAttribute("usuario");
        mav.setViewName("redirect:/professor/reunioes");
        mav.addObject("reunioes", professorService.listarReunioesByStatus(professor, filtro));
        return mav;
    }

    @GetMapping("/processos")
    public ModelAndView processos(ModelAndView mav, HttpSession session) {
        Usuario professor = (Usuario) session.getAttribute("usuario");
        mav.setViewName("professor/processos");
        mav.addObject("processos", professorService.listarProcessosDesignados(professor));
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
    public ModelAndView processoVotar(Processo processo, ModelAndView mav) {
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
