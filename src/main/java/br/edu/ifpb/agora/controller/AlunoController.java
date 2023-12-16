package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.Assunto;
import br.edu.ifpb.agora.model.NavPage;
import br.edu.ifpb.agora.model.NavPageBuilder;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.StatusEnum;
import br.edu.ifpb.agora.model.Usuario;
import br.edu.ifpb.agora.repository.AssuntoRepository;
import br.edu.ifpb.agora.service.AlunoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView salvarProcesso(@Valid Processo processo, BindingResult result, ModelAndView modelAndView, HttpSession session) {
        if (result.hasErrors()) {
            modelAndView.setViewName("aluno/tela-aluno-cadastro-processo");
            modelAndView.addObject("processo", processo);
            return modelAndView;

        }
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        alunoService.cadastraNovoProcesso(usuario.getId(), processo);
        modelAndView.setViewName("redirect:/aluno/processo");
        return modelAndView;

    }

    @GetMapping("/processo")
    public ModelAndView consultarProcessos(ModelAndView mav, HttpSession session, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page-1, size);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Page<Processo> processos = alunoService.consultaProcessos(usuario.getId(), paging);
        NavPage navPage = NavPageBuilder.newNavPage(processos.getNumber() + 1, processos.getTotalElements(), processos.getTotalPages(), size);
        mav.addObject("navPage", navPage);
        mav.setViewName("aluno/tela-aluno-listagem-processos");
        mav.addObject("processos", processos);
        return mav;

    }

    @GetMapping("/processo/consultar")
    public ModelAndView filtrarProcesso(@RequestParam(name = "filtro", defaultValue = "") String filtro, @RequestParam(name = "order", defaultValue = "") String order, ModelAndView modelAndView, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        modelAndView.addObject("processos", alunoService.filtrarProcesso(usuario.getId(), filtro, order));
        modelAndView.setViewName("/aluno/tela-aluno-listagem-processos");
        return modelAndView;
    }
}
