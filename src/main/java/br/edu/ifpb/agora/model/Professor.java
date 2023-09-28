package br.edu.ifpb.agora.model;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Professor extends Usuario{
    @ManyToMany
    private List<Colegiado> colegiados;

    private boolean coordenador;

    @OneToMany(mappedBy = "professor")
    private List<Voto> votos;
    public Professor() {
    }
    public Professor(String nome, String fone, String matricula, String login, String senha, boolean admin, boolean coordenador) {
        super(nome, fone, matricula, login, senha, admin);
        this.coordenador = coordenador;
    }
    private List<Colegiado> getColegiados() {
        return colegiados;
    }

    private void addColegiado(Colegiado colegiado) {
        colegiados.add(colegiado);
    }

    public boolean isCoordenador() {
        return coordenador;
    }

    public void setCoordenador(boolean coordenador) {
        this.coordenador = coordenador;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void addVoto(Voto voto) {
        votos.add(voto);
    }
}
