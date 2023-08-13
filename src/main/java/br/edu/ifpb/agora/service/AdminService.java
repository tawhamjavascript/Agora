package br.edu.ifpb.agora.service;

import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private AssuntoRepository assuntoRepository;

    private Admin admin;

    public AdminService() {
        this.admin = adminRepository.findById(1L).orElse(null);
        if (this.admin == null) {
            this.admin = new Admin();
            adminRepository.save(this.admin);
        }
    }

    @Transactional
    public void registerTeacher(Professor professor) {
        this.admin.addProfessor(professor);
        professorRepository.save(professor);
    }

    @Transactional
    public void removeTeacher(Professor professor) {
        this.admin.removeProfessor(professor);
        professorRepository.delete(professor);
    }
    @Transactional
    public void updateTeacher(Professor professor) {
        this.admin.updateProfessor(professor);
        professorRepository.save(professor);
    }

    public List<Professor> AllTeachers() {
        return this.admin.getProfessores();
    }

    public Professor getTeacher(Long id) {
        return professorRepository.findById(id).orElse(null);
    }

    @Transactional
    public void registerStudent(Aluno aluno) {
        this.admin.addAluno(aluno);
        alunoRepository.save(aluno);
    }

    @Transactional
    public void removeStudent(Aluno aluno) {
        this.admin.removeAluno(aluno);
        alunoRepository.delete(aluno);
    }
    @Transactional
    public void updateStudent(Aluno aluno) {
        this.admin.updateAluno(aluno);
        alunoRepository.save(aluno);
    }

    public List<Aluno> AllStudent() {
        return this.admin.getAlunos();
    }

    public Aluno getStudent(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }

    public void addCourse(Curso curso) {
        this.admin.addCurso(curso);
        cursoRepository.save(curso);
    }

    public void removeCourse(Curso curso) {
        this.admin.removeCurso(curso);
        cursoRepository.delete(curso);

    }

    public void updateCourse(Curso curso) {
        this.admin.updateCurso(curso);
        cursoRepository.save(curso);
    }

    public List<Curso> AllCourses() {
        return this.admin.getCursos();
    }

    public Curso getCourse(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }

    public void addCoordinator(Long id) {
        Professor professor = professorRepository.findById(id).orElse(null);
        if (professor == null) {
            return;
        }
        Coordenador coordenador = new Coordenador();
        professor.setCoordenador(coordenador);
        this.admin.addCoordenador(professor);
        coordenadorRepository.save(coordenador);

    }

    public void removeCoordinator(Long id) {
        Professor professor = professorRepository.findById(id).orElse(null);
        if (professor == null) {
            return;
        }
        Coordenador coordenador = professor.getCoordenador();
        if (coordenador == null) {
            return;
        }
        professor.setCoordenador(null);
        this.admin.removeCoordenador(professor);
        coordenadorRepository.delete(coordenador);
    }

    public void registerSubject(Assunto assunto) {
        this.admin.addAssunto(assunto);
        assuntoRepository.save(assunto);
    }

    public void removeSubject(Assunto assunto) {
        this.admin.removeAssunto(assunto);
        assuntoRepository.delete(assunto);
    }

    public void updateSubject(Assunto assunto) {
        this.admin.updateAssunto(assunto);
        assuntoRepository.save(assunto);
    }

    public List<Assunto> AllSubjects() {
        return this.admin.getAssuntos();
    }

    public Assunto getSubject(Long id) {
        return assuntoRepository.findById(id).orElse(null);
    }


}
