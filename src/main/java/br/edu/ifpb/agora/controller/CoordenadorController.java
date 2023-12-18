package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.service.ProfessorService;
import br.edu.ifpb.agora.service.PadraoProjeto.ProcessosTemporarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import br.edu.ifpb.agora.service.CoordenadorService;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/coordenador")
public class CoordenadorController {

    private HashMap<String, String> pathTo = new HashMap<String, String>();    

    private List<String> getPath(String page) {
        if(page.equals("listagem")) {
            return Arrays.asList("/css/main.css", "/css/coordenador/listagem.css");
        }

        return Arrays.asList("/css/coordenador/home.css");
    }


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
        pathTo.put("cadastrar", "/coordenador/sessao/cadastro");
        pathTo.put("listar", "/coordenador/processo");
        pathTo.put("logout", "/auth/logout");

        modelAndView.setViewName("coordenador/listagem-processos");
        modelAndView.addObject("processos", coordenadorService.listarTodosProcessosDoColegiado(principal));

        modelAndView.addObject("caminho", pathTo);
        modelAndView.addObject("stylePaths", getPath("listagem"));
        return modelAndView;
    }

    @GetMapping("/processo/consultar")
    public ModelAndView processosConsultar(@RequestParam(name = "filtro") String filtro, ModelAndView modelAndView, Principal principal) {
        pathTo.put("cadastrar", "/coordenador/sessao/cadastro");
        pathTo.put("listar", "/coordenador/processo");
        pathTo.put("logout", "/auth/logout");

        modelAndView.setViewName("coordenador/listagem-processos");
        modelAndView.addObject("processos", coordenadorService.filtro(principal, filtro));

        modelAndView.addObject("caminho", pathTo);
        modelAndView.addObject("stylePaths", getPath("listagem"));
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

    @GetMapping("/sessao")
    public ModelAndView getSessoes(Principal principal, ModelAndView mav) {
        mav.setViewName("coordenador/listagem-sessoes");
        mav.addObject("reunioes", coordenadorService.getReuniaoDoColegiado(principal));
        return mav;
    }
    
    @GetMapping("/home")
    public ModelAndView getHome(ModelAndView mav) {
        mav.setViewName("coordenador/home");
        mav.addObject("stylePaths", getPath(""));
        return mav;
    }

    
    
    @GetMapping("/sessao/cadastro")
    public ModelAndView getCadastroSessao(Principal principal, ModelAndView mav) {
        mav.setViewName("coordenador/cadastro-sessao");
        mav.addObject("reuniao", new Reuniao());
        mav.addObject("processos", coordenadorService.getProcessosEmMemoria(principal));
        return mav;
    }

    @PostMapping("/sessao/cadastro")
    public ModelAndView CadastroReuniao(Reuniao reuniao, ModelAndView mav, Principal principal) {
        coordenadorService.salvarReuniao(principal, reuniao);
        mav.setViewName("redirect:/coordenador/sessao");
        return mav;
        
        
       
    }
    

    @GetMapping("/sessao/add/processo")
    public ModelAndView getCadastroProcesso(Principal principal, ModelAndView mav) {
        mav.setViewName("coordenador/adicionar-processo");
        mav.addObject("processos", coordenadorService.getProcessosDoCursoEComDecisaoRelator(principal));
        return mav;

    }

    @PostMapping("/sessao/add/processo")
    public ModelAndView salvarProcessoEmMemoria(Long id, Principal principal, ModelAndView mav) {
        coordenadorService.salvarProcessoEmMemoria(principal, id);
        mav.setViewName("redirect:/coordenador/sessao/add/processo");
        return mav;
        
    }
    
}
