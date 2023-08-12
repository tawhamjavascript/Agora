package br.edu.ifpb.agora.model;


import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario{
    private Coordenador coordenador;
    private List<Colegiado> colegiados;
    public Professor() {
    }
    public Professor(String nome, String fone, String matricula, String login, String senha, Coordenador coordenador) {
        super(nome, fone, matricula, login, senha);
        this.coordenador = coordenador;
    }

    public Coordenador getCoordenador() {
        return coordenador;
    }



    private List<Colegiado> getColegiados() {
        return colegiados;
    }

    private void addColegiado(Colegiado colegiado) {
        colegiados.add(colegiado);
    }


}
