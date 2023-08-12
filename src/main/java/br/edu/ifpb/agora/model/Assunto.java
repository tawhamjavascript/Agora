package br.edu.ifpb.agora.model;

public class Assunto {
    private long id;
    private String nome;

    public Assunto() {
    }

    public Assunto(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
