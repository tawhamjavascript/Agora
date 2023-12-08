package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.Assunto;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.StatusEnum;
import br.edu.ifpb.agora.model.Usuario;
import br.edu.ifpb.agora.repository.AssuntoRepository;
import br.edu.ifpb.agora.service.AlunoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/aluno")

public class AlunoController {

    private HashMap<String, String> pathTo = new HashMap<String, String>();    

    private List<String> getPath(String page) {
    if(page.equals("cadastro")) {
            return Arrays.asList("/css/aluno/main.css", "/css/aluno/cadastro-processo-aluno.css");
        } else if (page.equals("listagem")) {
            return Arrays.asList("/css/aluno/main.css", "/css/aluno/aluno.css");
        }
        return Arrays.asList("/css/aluno/main.css");
    }

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @GetMapping("/processo/cadastrar")
    public ModelAndView cadastrarProcesso(ModelAndView mav) {
        pathTo.put("novoProcesso", "/aluno/processo/cadastrar");        
        pathTo.put("listar", "/aluno/processo");
        pathTo.put("logout", "/auth/logout");

        mav.setViewName("aluno/tela-aluno-cadastro-processo");
        mav.addObject("processo", new Processo());

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("cadastro"));
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
    public ModelAndView consultarProcessos(ModelAndView mav, HttpSession session) {
        pathTo.put("novoProcesso", "/aluno/processo/cadastrar");        
        pathTo.put("listar", "/aluno/processo");
        pathTo.put("logout", "/auth/logout");

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        mav.setViewName("aluno/tela-aluno-listagem-processos");
        mav.addObject("processos", alunoService.consultaProcessos(usuario.getId()));

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("listagem"));
        return mav;

    }

    @GetMapping("/processo/consultar")
    public ModelAndView filtrarProcesso(@RequestParam(name = "filtro", defaultValue = "") String filtro, @RequestParam(name = "order", defaultValue = "") String order, ModelAndView modelAndView, HttpSession session) {
        pathTo.put("novoProcesso", "/aluno/processo/cadastrar");        
        pathTo.put("listar", "/aluno/processo");
        pathTo.put("logout", "/auth/logout");
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        modelAndView.addObject("processos", alunoService.filtrarProcesso(usuario.getId(), filtro, order));
        modelAndView.setViewName("/aluno/tela-aluno-listagem-processos");

        modelAndView.addObject("caminho", pathTo);
        modelAndView.addObject("stylePaths", getPath("listagem"));
        return modelAndView;
    }
}
