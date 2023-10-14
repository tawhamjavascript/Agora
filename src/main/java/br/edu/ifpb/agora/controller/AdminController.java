package br.edu.ifpb.agora.controller;

import br.edu.ifpb.agora.model.Assunto;
import br.edu.ifpb.agora.model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.agora.service.AdminService;

@Controller
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/curso")
    public ModelAndView getCursos(ModelAndView mav) {
        mav.setViewName("admin/listagem-cursos");
        mav.addObject("cursos", adminService.allCourses());
        return mav;
    }

    @GetMapping("/curso/cadastro")
    public ModelAndView getCadastro(ModelAndView mav) {
        mav.setViewName("admin/cadastro-curso");
        mav.addObject("curso", new Curso());
        return mav;
    }

    @GetMapping("/curso/{id}")
    public ModelAndView editCurso(@PathVariable(value = "id") Long id, ModelAndView mav) {
        mav.setViewName("admin/cadastro-curso");
        mav.addObject("curso", adminService.getCourse(id));
        return mav;
    }

    @PostMapping("/curso")
    public ModelAndView saveCurso(Curso curso, ModelAndView mav) {
        adminService.addCourse(curso);
        mav.setViewName("redirect:/admin/curso");
        mav.addObject("cursos", adminService.allCourses());
        return mav;
    }

    @DeleteMapping("/curso/{id}")
    public ModelAndView deleteCurso(@PathVariable(value = "id") Long id, ModelAndView mav) {
        adminService.removeCourse(id);
        mav.setViewName("redirect:/admin/curso");
        mav.addObject("cursos", adminService.allCourses());
        return mav;
    }


    @GetMapping("assunto")
    public ModelAndView getAssuntos(ModelAndView mav) {
        mav.setViewName("admin/listagem-assunto-processo-reuniao");
        mav.addObject("assuntos", adminService.allSubjects());
        return mav;
    }

    @GetMapping("assunto/cadastro")
    public ModelAndView getCadastroAssunto(ModelAndView mav) {
        mav.setViewName("admin/cadastro-assunto-processo-reuniao");
        mav.addObject("assunto", new Assunto());
        return mav;
    }

    @PostMapping("assunto")
    public ModelAndView saveAssunto(Assunto assunto, ModelAndView mav) {
        adminService.registerSubject(assunto);
        mav.setViewName("redirect:/admin/assunto");
        mav.addObject("assuntos", adminService.allSubjects());
        return mav;
    }

    @GetMapping("assunto/{id}")
    public ModelAndView editAssunto(@PathVariable(value = "id") Long id, ModelAndView mav) {
        mav.setViewName("admin/cadastro-assunto-processo-reuniao");
        mav.addObject("assunto", adminService.getSubject(id));
        return mav;
    }

    @DeleteMapping("assunto/{id}")
    public ModelAndView deleteAssunto(@PathVariable(value = "id") Long id, ModelAndView mav) {
        adminService.removeSubject(id);
        mav.setViewName("redirect:/admin/assunto");
        mav.addObject("assuntos", adminService.allSubjects());
        return mav;
    }
}
