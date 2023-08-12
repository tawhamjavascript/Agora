package br.edu.ifpb.agora.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Coordenador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany
    @JoinColumn(name = "coordenador_id")
    private List<Reuniao> reunioes;

    @OneToMany
    @JoinColumn(name = "coordenador_id")
    private List<Processo> processos;

    @OneToMany
    @JoinColumn(name = "coordenador_id")
    private List<Colegiado> colegiados;

    public Coordenador() {
    }

    public List<Reuniao> getReunioes() {
        return reunioes;
    }

    public void addReuniao(Reuniao reuniao) {
        reunioes.add(reuniao);
    }

    public List<Processo> getProcessos() {
        return processos;
    }

    public void addProcesso(Processo processo) {
        processos.add(processo);
    }

    public List<Colegiado> getColegiados() {
        return colegiados;
    }

    public void addColegiado(Colegiado colegiado) {
        colegiados.add(colegiado);
    }
}
