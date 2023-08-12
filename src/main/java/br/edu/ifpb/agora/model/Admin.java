package br.edu.ifpb.agora.model;

import java.util.ArrayList;
import java.util.List;

public class Admin {
    private List<Professor> professores;
    private List<Aluno> alunos;
    private List<Curso> cursos;
    private Professor coordenador;
    private List<Usuario> usuarios;
    private List<Assunto> assuntos;

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
