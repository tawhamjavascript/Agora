package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.service.ProfessorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import br.edu.ifpb.agora.service.CoordenadorService;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/coordenador")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;


    @ModelAttribute("statusItens")
    public List<StatusEnum> getStatus() {
        return List.of(StatusEnum.values());
    }

    @ModelAttribute("relatorItens")
    public List<Professor> getRelator(Principal user) {
        return coordenadorService.listarTodosProfessoresDoColegiado(user);

    }

    @ModelAttribute("alunoItens")
    public List<Aluno> getAluno(Principal principal) {
        
        return coordenadorService.listarTodosAlunosProcesso(principal);

    }

    @GetMapping("/processo")
    public ModelAndView processos(ModelAndView modelAndView, Principal principal) {
        modelAndView.setViewName("coordenador/listagem-processos");
        modelAndView.addObject("processos", coordenadorService.listarTodosProcessosDoColegiado(principal));
        return modelAndView;
    }

    @GetMapping("/processo/consultar")
    public ModelAndView processosConsultar(@RequestParam(name = "filtro") String filtro, ModelAndView modelAndView, Principal principal) {
        modelAndView.setViewName("coordenador/listagem-processos");
        modelAndView.addObject("processos", coordenadorService.filtro(principal, filtro));
        return modelAndView;
    }

    @GetMapping("/processo/{id}/relator")
    public ModelAndView getProcesso(@PathVariable("id") Long idProcesso, ModelAndView mav) {
        mav.setViewName("coordenador/adicionar-relator");
        mav.addObject("processo", coordenadorService.getProcesso(idProcesso));
        return mav;
    }

    @PostMapping("/processo/relator")
    public ModelAndView setRelator(Processo processo, ModelAndView mav) {
        coordenadorService.salvarProcesso(processo);
        mav.setViewName("redirect:/coordenador/processo");
        return mav;
    }
}
