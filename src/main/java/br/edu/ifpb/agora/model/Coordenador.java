package br.edu.ifpb.agora.model;

import java.util.ArrayList;
import java.util.List;

public class Coordenador {
    private List<Reuniao> reunioes;
    private List<Processo> processos;
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
