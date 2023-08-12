package br.edu.ifpb.agora.model;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity
public class Professor extends Usuario{

    @OneToOne
    private Coordenador coordenador;
    @ManyToMany
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
