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

    private AlunoRepository alunoRepository;
    @Autowired

    private CursoRepository cursoRepository;

    @Autowired
    private AssuntoRepository assuntoRepository;





    @Transactional
    public void registerTeacher(Professor professor) {
        professorRepository.save(professor);
    }

    @Transactional
    public void removeTeacher(Professor professor) {
        professorRepository.delete(professor);
    }
    @Transactional
    public void updateTeacher(Professor professor) {
        professorRepository.save(professor);
    }

    public List<Professor> AllTeachers() {

        return professorRepository.findAll();
    }

    public Professor getTeacher(Long id) {
        return professorRepository.findById(id).orElse(null);
    }

    @Transactional
    public void registerStudent(Aluno aluno) {

        alunoRepository.save(aluno);
    }

    @Transactional
    public void removeStudent(Aluno aluno) {
        alunoRepository.delete(aluno);
    }
    @Transactional
    public void updateStudent(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> AllStudent() {
        return alunoRepository.findAll();
    }

    public Aluno getStudent(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }

    @Transactional

    public void addCourse(Curso curso) {
        cursoRepository.save(curso);
    }

    @Transactional

    public void removeCourse(Curso curso) {
        cursoRepository.delete(curso);

    }

    @Transactional
    public void updateCourse(Curso curso) {
        cursoRepository.save(curso);
    }

    public List<Curso> AllCourses() {

        return cursoRepository.findAll();
    }

    public Curso getCourse(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addCoordinator(Long id) {
        Professor professor = professorRepository.findById(id).orElse(null);
        if (professor == null) {
            return;
        }
        professor.setCoordenador(true);
        professorRepository.save(professor);
    }

    @Transactional

    public void registerSubject(Assunto assunto) {

        assuntoRepository.save(assunto);
    }

    @Transactional

    public void removeSubject(Assunto assunto) {
        assuntoRepository.delete(assunto);
    }

    @Transactional

    public void updateSubject(Assunto assunto) {

        assuntoRepository.save(assunto);
    }

    public List<Assunto> AllSubjects() {
        return this.assuntoRepository.findAll();
    }

    public Assunto getSubject(Long id) {
        return assuntoRepository.findById(id).orElse(null);
    }


}
