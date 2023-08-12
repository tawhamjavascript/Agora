package br.edu.ifpb.agora.model;

public class Curso {
    private long id;
    private String nome;

    public Curso() {

    }

    public Curso(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

}
