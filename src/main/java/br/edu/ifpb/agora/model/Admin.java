package br.edu.ifpb.agora.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany
    @JoinColumn(name = "admin_id")
    private List<Professor> professores;
    @OneToMany
    @JoinColumn(name = "admin_id")
    private List<Aluno> alunos;
    @OneToMany
    @JoinColumn(name = "admin_id")
    private List<Curso> cursos;

    @OneToMany
    @JoinColumn(name = "admin_id")
    private List<Professor> coordenador;

    @OneToMany
    @JoinColumn(name = "admin_id")
    private List<Assunto> assuntos;


    public Admin() {

    }

    public long getId() {
        return id;
    }


    public List<Professor> getProfessores() {
        return professores;
    }

    public void addProfessor(Professor professor) {
        professores.add(professor);
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void addAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void addCurso(Curso curso) {
        cursos.add(curso);
    }

    public List<Professor> getCoordenador() {
        return coordenador;
    }

    public void addCoordenador(Professor coordenador) {
        this.coordenador.add(coordenador);
    }


    public List<Assunto> getAssuntos() {
        return assuntos;
    }

    public void addAssunto(Assunto assunto) {
        assuntos.add(assunto);
    }
}
