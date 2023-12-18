package br.edu.ifpb.agora.controller;

import br.edu.ifpb.agora.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.agora.service.AdminService;
import jakarta.validation.Valid;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin")

public class AdminController {

    private HashMap<String, String> pathTo = new HashMap<String, String>();    

    private List<String> getPath(String page) {
        if(page.equals("cadastro")) {
            return Arrays.asList("/css/main.css", "/css/admin/admin.css", "/css/admin/cadastro.css", "/css/admin/listagem.css");
        } else if (page.equals("listagem")) {
            return Arrays.asList("/css/main.css", "/css/admin/admin.css", "/css/admin/listagem.css");
        }
        return Arrays.asList("/css/admin/homepage.css");
    }

    @Autowired
    private AdminService adminService;


    @GetMapping("/home")
    public ModelAndView getHome(ModelAndView mav) {
        mav.setViewName("admin/homepage");
        mav.addObject("stylePaths", getPath(""));
        return mav;
    }

    @GetMapping("/curso")
    public ModelAndView getCursos(ModelAndView mav) {
        pathTo.put("incluir", "/admin/curso/cadastro");
        pathTo.put("listar", "/admin/curso");        
        pathTo.put("home", "/admin/home");

        mav.setViewName("admin/listagem-cursos");
        mav.addObject("cursos", adminService.allCourses());

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("listagem"));
        return mav;
    }

    @GetMapping("/curso/cadastro")
    public ModelAndView getCadastro(ModelAndView mav) {
        pathTo.put("incluir", "/admin/curso/cadastro");
        pathTo.put("listar", "/admin/curso");
        pathTo.put("home", "/admin/home");

        mav.setViewName("admin/cadastro-curso");
        mav.addObject("curso", new Curso());

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("cadastro"));
        return mav;
    }

    @GetMapping("/curso/{id}")
    public ModelAndView editCurso(@PathVariable(value = "id") Long id, ModelAndView mav) {
        pathTo.put("incluir", "/admin/curso/cadastro");
        pathTo.put("listar", "/admin/curso");        
        pathTo.put("home", "/admin/home");

        mav.setViewName("admin/cadastro-curso");
        mav.addObject("curso", adminService.getCourse(id));
        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("cadastro"));
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

    @GetMapping("/curso/deletar/{id}")
    public ModelAndView deleteCurso(@PathVariable(value = "id") Long id, ModelAndView mav) {
        adminService.removeCourse(id);
        mav.setViewName("redirect:/admin/curso");
        mav.addObject("cursos", adminService.allCourses());
        return mav;
    }


    @GetMapping("/assunto")
    public ModelAndView getAssuntos(ModelAndView mav) {
        pathTo.put("incluir", "/admin/assunto/cadastro");
        pathTo.put("listar", "/admin/assunto");        
        pathTo.put("home", "/admin/home");

        mav.setViewName("admin/listagem-assunto-processo-reuniao");
        mav.addObject("assuntos", adminService.allSubjects());

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("listagem"));
        return mav;
    }

    @GetMapping("/assunto/cadastro")
    public ModelAndView getCadastroAssunto(ModelAndView mav) {
        pathTo.put("incluir", "/admin/assunto/cadastro");
        pathTo.put("listar", "/admin/assunto");
        pathTo.put("home", "/admin/home");

        mav.setViewName("admin/cadastro-assunto-processo-reuniao");
        mav.addObject("assunto", new Assunto());

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("cadastro"));
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

    @GetMapping("/assunto/{id}")
    public ModelAndView editAssunto(@PathVariable(value = "id") Long id, ModelAndView mav) {
        pathTo.put("incluir", "/admin/assunto/cadastro");
        pathTo.put("listar", "/admin/assunto");        
        pathTo.put("home", "/admin/home");

        mav.setViewName("admin/cadastro-assunto-processo-reuniao");
        mav.addObject("assunto", adminService.getSubject(id));

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("cadastro"));
        return mav;
    }

    @GetMapping("/assunto/deletar/{id}")
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

    @GetMapping("/aluno")
    public ModelAndView getAlunos(ModelAndView mav) {
        pathTo.put("incluir", "/admin/aluno/cadastro");
        pathTo.put("listar", "/admin/aluno");
        pathTo.put("home", "/admin/home");

        mav.setViewName("admin/listagem-aluno");
        mav.addObject("alunos", adminService.allStudent());

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("listagem"));
        return mav;
    }

    @GetMapping("/aluno/cadastro")
    public ModelAndView getCadastroAluno(ModelAndView mav) {
        pathTo.put("incluir", "/admin/aluno/cadastro");
        pathTo.put("listar", "/admin/aluno");
        pathTo.put("home", "/admin/home");

        mav.setViewName("admin/cadastrar-aluno");
        mav.addObject("aluno", new Aluno());

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("cadastro"));
        return mav;
    }

    @PostMapping("/aluno")
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

    @GetMapping("/aluno/{id}")
    public ModelAndView editAluno(@PathVariable(value = "id") Long id, ModelAndView mav) {
        pathTo.put("incluir", "/admin/aluno/cadastro");
        pathTo.put("listar", "/admin/aluno");
        pathTo.put("home", "/admin/home");

        mav.setViewName("admin/cadastrar-aluno");
        mav.addObject("aluno", adminService.getStudent(id));

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("cadastro"));
        return mav;
    }

    @GetMapping("/aluno/deletar/{id}")
    public ModelAndView deleteAluno(@PathVariable(value = "id") Long id, ModelAndView mav) {
        adminService.removeStudent(id);
        mav.setViewName("redirect:/admin/aluno");
        mav.addObject("alunos", adminService.allStudent());
        return mav;
    }

    @GetMapping("/professor")
    public ModelAndView getProfessores(ModelAndView mav) {
        pathTo.put("incluir", "/admin/professor/cadastro");
        pathTo.put("listar", "/admin/professor");        
        pathTo.put("home", "/admin/home");
        mav.setViewName("admin/listagem-professor");
        mav.addObject("professores", adminService.allTeachers());
        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("listagem"));
        return mav;
    }

    @GetMapping("/professor/cadastro")
    public ModelAndView getCadastroProfessor(ModelAndView mav) {
        pathTo.put("incluir", "/admin/professor/cadastro");
        pathTo.put("listar", "/admin/professor");        
        pathTo.put("home", "/admin/home");
        mav.setViewName("admin/cadastro-professor");
        mav.addObject("professor", new Professor());
        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("cadastro"));
        return mav;
    }

    @PostMapping("/professor")
    public ModelAndView saveProfessor(@Valid Professor professor, BindingResult result, ModelAndView mav) {
        pathTo.put("incluir", "/admin/professor/cadastro");
        pathTo.put("listar", "/admin/professor");        
        pathTo.put("home", "/admin/home");
        if (result.hasErrors()){
            mav.setViewName("admin/cadastro-professor");
            mav.addObject("professor", professor);
            return mav;
        }
        adminService.registerTeacher(professor);
        mav.setViewName("redirect:/admin/professor");
        mav.addObject("professores", adminService.allTeachers());
        mav.addObject("caminho", pathTo);
        return mav;
    }

    @GetMapping("/professor/{id}")
    public ModelAndView editProfessor(@PathVariable(value = "id") Long id, ModelAndView mav) {
        pathTo.put("incluir", "/admin/professor/cadastro");
        pathTo.put("listar", "/admin/professor");        
        pathTo.put("home", "/admin/home");
        mav.setViewName("admin/cadastro-professor");
        mav.addObject("professor", adminService.getTeacher(id));
        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("cadastro"));
        return mav;
    }

    @GetMapping("/professor/deletar/{id}")
    public ModelAndView deleteProfessor(@PathVariable(value = "id") Long id, ModelAndView mav) {
        adminService.removeTeacher(id);
        mav.setViewName("redirect:/admin/professor");
        mav.addObject("professores", adminService.allTeachers());
        return mav;
    }


    @GetMapping("/colegiado")
    public ModelAndView getColegiados(ModelAndView mav) {
        pathTo.put("incluir", "/admin/colegiado/cadastrar");
        pathTo.put("listar", "/admin/colegiado");        
        pathTo.put("home", "/admin/home");

        mav.setViewName("admin/listagem-colegiados");
        mav.addObject("colegiados", adminService.allColegiados());

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("listagem"));
        return mav;
    }

    @GetMapping("/colegiado/{id}")
    public ModelAndView editarColegiado(@PathVariable(value = "id") Long id, ModelAndView mav) {
        pathTo.put("incluir", "/admin/colegiado/cadastrar");
        pathTo.put("listar", "/admin/colegiado");        
        pathTo.put("home", "/admin/home");
        mav.setViewName("admin/cadastro-colegiados");
        mav.addObject("colegiado", adminService.getColegiado(id));
        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("cadastro"));
        return mav;
    }

    @GetMapping("/colegiado/cadastrar")
    public ModelAndView getCadastroColegiado(ModelAndView mav) {
        pathTo.put("incluir", "/admin/colegiado/cadastrar");
        pathTo.put("listar", "/admin/colegiado");        
        pathTo.put("home", "/admin/home");

        mav.setViewName("admin/cadastro-colegiados");
        mav.addObject("colegiado", new Colegiado());

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("cadastro"));
        return mav;
    }

    @PostMapping("/colegiado")
    public ModelAndView saveColegiado(@Valid Colegiado colegiado, BindingResult result, ModelAndView mav) {
        if (result.hasErrors()){
            if(colegiado.getId() != null) {
                Colegiado colegiadoBD = adminService.getColegiado(colegiado.getId());
                colegiado.setMembros(colegiadoBD.getMembros());
            }

            mav.setViewName("admin/cadastro-colegiados");
            mav.addObject("colegiado",colegiado);
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
    @GetMapping("/colegiado/{id}/membros")
    public ModelAndView getAddMembros(@PathVariable(value = "id") Long id, ModelAndView mav) {
        mav.setViewName("admin/adicionar-membro");

        mav.addObject("colegiadoId", id);
        mav.addObject("professores", adminService.allTeachersOfCourse(id));
        return mav;
    }

    @PostMapping("/colegiado/membros")
    public ModelAndView salvarMembro(Long idColegiado, Long idProfessor, ModelAndView mav) {
        adminService.adicionarMembro(idColegiado, idProfessor);
        mav.setViewName("redirect:/admin/colegiado/" + idColegiado + "/membros");
        return mav;

    }

    @DeleteMapping("/colegiado/{id}/membros/delete/{idProfessor}")
    public ModelAndView deletarMembro(@PathVariable(value = "id") Long idColegiado, @PathVariable(value = "idProfessor") Long idProfessor, ModelAndView mav) {
        adminService.deletarMembro(idColegiado, idProfessor);
        mav.setViewName("redirect:/admin/colegiado/" + idColegiado);
        return mav;

    }
}
