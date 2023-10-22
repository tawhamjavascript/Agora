package br.edu.ifpb.agora.controller;

import br.edu.ifpb.agora.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.agora.service.AdminService;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private AdminService adminService;


    @GetMapping("/home")
    public ModelAndView getHome(ModelAndView mav) {
        mav.setViewName("admin/homepage");
        return mav;
    }

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
    public ModelAndView saveCurso(@Valid Curso curso, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()){
            mav.addObject("curso", curso);
            mav.setViewName("/admin/cadastro-curso");
            return mav;
        }
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


    @GetMapping("/assunto")
    public ModelAndView getAssuntos(ModelAndView mav) {
        mav.setViewName("admin/listagem-assunto-processo-reuniao");
        mav.addObject("assuntos", adminService.allSubjects());
        return mav;
    }

    @GetMapping("/assunto/cadastro")
    public ModelAndView getCadastroAssunto(ModelAndView mav) {
        mav.setViewName("admin/cadastro-assunto-processo-reuniao");
        mav.addObject("assunto", new Assunto());
        return mav;
    }

    @PostMapping("/assunto")
    public ModelAndView saveAssunto(@Valid Assunto assunto, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()){
            mav.setViewName("admin/cadastro-assunto-processo-reuniao");
            mav.addObject("assunto", assunto);
            return mav;
        }
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

    @ModelAttribute("cursoItems")
    public List<Curso> getCursos() {
        return adminService.allCourses();
    }

    @GetMapping("aluno")
    public ModelAndView getAlunos(ModelAndView mav) {
        mav.setViewName("admin/listagem-aluno");
        mav.addObject("alunos", adminService.allStudent());
        return mav;
    }

    @GetMapping("aluno/cadastro")
    public ModelAndView getCadastroAluno(ModelAndView mav) {
        mav.setViewName("admin/cadastrar-aluno");
        mav.addObject("aluno", new Aluno());
        return mav;
    }

    @PostMapping("aluno")
    public ModelAndView saveAluno(@Valid Aluno aluno, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()){
            mav.setViewName("admin/cadastrar-aluno");
            mav.addObject("aluno", aluno);
            return mav;
        }
        adminService.registerStudent(aluno);
        mav.setViewName("redirect:/admin/aluno");
        mav.addObject("alunos", adminService.allStudent());
        return mav;
    }

    @GetMapping("aluno/{id}")
    public ModelAndView editAluno(@PathVariable(value = "id") Long id, ModelAndView mav) {
        mav.setViewName("admin/cadastrar-aluno");
        mav.addObject("aluno", adminService.getStudent(id));
        return mav;
    }

    @DeleteMapping("aluno/{id}")
    public ModelAndView deleteAluno(@PathVariable(value = "id") Long id, ModelAndView mav) {
        adminService.removeStudent(id);
        mav.setViewName("redirect:/admin/aluno");
        mav.addObject("alunos", adminService.allStudent());
        return mav;
    }

    @GetMapping("professor")
    public ModelAndView getProfessores(ModelAndView mav) {
        mav.setViewName("admin/listagem-professor");
        mav.addObject("professores", adminService.allTeachers());
        return mav;
    }

    @GetMapping("professor/cadastro")
    public ModelAndView getCadastroProfessor(ModelAndView mav) {
        mav.setViewName("admin/cadastro-professor");
        mav.addObject("professor", new Professor());
        return mav;
    }

    @PostMapping("professor")
    public ModelAndView saveProfessor(@Valid Professor professor, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()){
            mav.setViewName("admin/cadastro-professor");
            mav.addObject("aluno", professor);
            return mav;
        }
        adminService.registerTeacher(professor);
        mav.setViewName("redirect:/admin/professor");
        mav.addObject("professores", adminService.allTeachers());
        return mav;
    }

    @GetMapping("professor/{id}")
    public ModelAndView editProfessor(@PathVariable(value = "id") Long id, ModelAndView mav) {
        mav.setViewName("admin/cadastro-professor");
        mav.addObject("professor", adminService.getTeacher(id));
        return mav;
    }

    @DeleteMapping("professor/{id}")
    public ModelAndView deleteProfessor(@PathVariable(value = "id") Long id, ModelAndView mav) {
        adminService.removeTeacher(id);
        mav.setViewName("redirect:/admin/professor");
        mav.addObject("professores", adminService.allTeachers());
        return mav;
    }


    @GetMapping("colegiado")
    public ModelAndView getColegiados(ModelAndView mav) {
        mav.setViewName("admin/listagem-colegiados");
        mav.addObject("colegiados", adminService.allColegiados());
        return mav;
    }

    @GetMapping("colegiado/{id}")
    public ModelAndView editarColegiado(@PathVariable(value = "id") Long id, ModelAndView mav) {
        mav.setViewName("admin/cadastro-colegiados");
        mav.addObject("colegiado", adminService.getColegiado(id));
        return mav;
    }

    @GetMapping("colegiado/cadastrar")
    public ModelAndView getCadastroColegiado(ModelAndView mav) {
        mav.setViewName("admin/cadastro-colegiados");
        mav.addObject("colegiado", new Colegiado());
        return mav;
    }

    @PostMapping("colegiado")
    public ModelAndView saveColegiado(@Valid Colegiado colegiado, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()){
            mav.setViewName("admin/cadastro-colegiados");
            mav.addObject("colegiado", colegiado);
            return mav;
        }
        if (colegiado.getId() == null) {
            adminService.salvarColegiado(colegiado);
            mav.setViewName("redirect:/admin/colegiado/" + colegiado.getId() + "/membros");

        }
        else {
            adminService.salvarColegiado(colegiado);
            mav.setViewName("redirect:/admin/colegiado");
        }
        return mav;

    }
    @GetMapping("colegiado/{id}/membros")
    public ModelAndView getAddMembros(@PathVariable(value = "id") Long id, ModelAndView mav) {
        mav.setViewName("admin/adicionar-membro");

        mav.addObject("colegiadoId", id);
        mav.addObject("professores", adminService.allTeachersOfCourse(id));
        return mav;
    }

    @PostMapping("colegiado/membros")
    public ModelAndView salvarMembro(Long idColegiado, Long idProfessor, ModelAndView mav) {
        adminService.adicionarMembro(idColegiado, idProfessor);
        mav.setViewName("redirect:/admin/colegiado/" + idColegiado + "/membros");
        return mav;

    }

    @DeleteMapping("colegiado/{id}/membros/{idProfessor}")
    public ModelAndView deletarMembro(@PathVariable(value = "id") Long idColegiado, @PathVariable(value = "idProfessor") Long idProfessor, ModelAndView mav) {
        adminService.deletarMembro(idColegiado, idProfessor);
        mav.setViewName("/admin/cadastro-colegiados");
        mav.addObject("colegiado", adminService.getColegiado(idColegiado));
        return mav;

    }

}
