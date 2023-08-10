package br.edu.ifpb.agora.model;

import java.util.ArrayList;
import java.util.List;

public class Coordenador {
    private final List<Reuniao> reunioes = new ArrayList<>();
    private final List<Processo> processos = new ArrayList<>();
    private final List<Colegiado> colegiados = new ArrayList<>();

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
