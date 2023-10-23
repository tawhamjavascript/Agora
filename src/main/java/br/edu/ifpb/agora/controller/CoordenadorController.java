package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.service.ProfessorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import br.edu.ifpb.agora.service.CoordenadorService;
import org.springframework.web.servlet.ModelAndView;

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
    public List<Professor> getRelator(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return coordenadorService.listarTodosProfessoresDoColegiado(usuario.getId());

    }

    @ModelAttribute("alunoItens")
    public List<Aluno> getAluno(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return coordenadorService.listarTodosAlunosProcesso(usuario.getCurso().getId());

    }

    @GetMapping("/processo")
    public ModelAndView processos(ModelAndView modelAndView, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        modelAndView.setViewName("coordenador/listagem-processos");
        modelAndView.addObject("processos", coordenadorService.listarTodosProcessosDoColegiado(usuario.getId()));
        return modelAndView;
    }

    @PostMapping("/processo/consultar")
    public ModelAndView processosConsultar(String filtro, ModelAndView modelAndView, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        modelAndView.setViewName("coordenador/listagem-processos");
        modelAndView.addObject("processos", coordenadorService.filtro(usuario.getCurso().getId(), filtro));
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
