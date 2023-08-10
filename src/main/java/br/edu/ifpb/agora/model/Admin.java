package br.edu.ifpb.agora.model;

import java.util.ArrayList;
import java.util.List;

public class Admin {
    private final List<Professor> professores = new ArrayList<>();
    private final List<Aluno> alunos = new ArrayList<>();
    private final List<Curso> cursos = new ArrayList<>();
    private Professor coordenador;
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Assunto> assuntos = new ArrayList<>();

    public Admin() {

    }
    public Admin(Professor coordenador) {
        this.coordenador = coordenador;
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

    public Professor getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Professor coordenador) {
        this.coordenador = coordenador;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Assunto> getAssuntos() {
        return assuntos;
    }

    public void addAssunto(Assunto assunto) {
        assuntos.add(assunto);
    }




}
