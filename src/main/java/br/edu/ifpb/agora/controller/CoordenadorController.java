package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.Aluno;
import br.edu.ifpb.agora.model.StatusEnum;
import br.edu.ifpb.agora.model.Usuario;
import br.edu.ifpb.agora.service.ProfessorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.edu.ifpb.agora.service.CoordenadorService;
import org.springframework.web.servlet.ModelAndView;
import br.edu.ifpb.agora.model.Professor;

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
    public void processosConsultar(String filtro, ModelAndView modelAndView) {



    }
}
