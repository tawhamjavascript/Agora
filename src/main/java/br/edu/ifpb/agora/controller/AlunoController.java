package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.Assunto;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.StatusEnum;
import br.edu.ifpb.agora.repository.AssuntoRepository;
import br.edu.ifpb.agora.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/aluno")

public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @GetMapping("/processo/cadastrar")
    public ModelAndView cadastrarProcesso(ModelAndView mav) {
        mav.setViewName("aluno/tela-aluno-cadastro-processo");
        mav.addObject("processo", new Processo());
        return mav;
    }

    @ModelAttribute("assuntoItens")
    public List<Assunto> getCorrentistas() {
        return assuntoRepository.findAll();
    }

    @ModelAttribute("statusItens")
    public List<StatusEnum> getStatus() {
        return List.of(StatusEnum.values());
    }

    @PostMapping("/processo/cadastrar")
    public ModelAndView salvarProcesso(Processo processo, ModelAndView modelAndView) {
        alunoService.cadastraNovoProcesso(processo);
        modelAndView.setViewName("redirect:/aluno/processo");
        modelAndView.addObject("processos", alunoService.consultaProcessos());
        return modelAndView;

    }

    @GetMapping("/processo")
    public ModelAndView consultarProcessos(ModelAndView mav) {
        mav.setViewName("aluno/tela-aluno-listagem-processos");
        mav.addObject("processos", alunoService.consultaProcessos());
        return mav;

    }

    @PostMapping("/processo/consultar")
    public ModelAndView filtrarProcesso(String filtro, String order, ModelAndView modelAndView) {
        modelAndView.addObject("processos", alunoService.filtrarProcesso(filtro, order));
        modelAndView.setViewName("redirect:/aluno/processo");
        return modelAndView;
    }










}
