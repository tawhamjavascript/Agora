package br.edu.ifpb.agora.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Colegiado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date dataInicio;
    private Date dataFim;
    private String descricao;
    private String portaria;

    @OneToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @ManyToMany(mappedBy = "colegiados")
    private List<Professor> membros;

    @OneToMany(mappedBy = "colegiado")
    private List<Reuniao> reunioes;


    public Colegiado() {
    }

    public Colegiado(Date dataInicio, Date dataFim, String descricao, String portaria, Curso curso) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.descricao = descricao;
        this.portaria = portaria;
        this.curso = curso;
    }

    public Long getId() {
        return id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPortaria() {
        return portaria;
    }

    public Curso getCurso() {
        return curso;
    }

    public void addMembro(Professor membro) {
        membros.add(membro);
    }

    public List<Professor> getMembros() {
        return membros;
    }

    public List<Reuniao> getReunioes() {
        return reunioes;
    }

    public void addReuniao(Reuniao reuniao) {
        reunioes.add(reuniao);
    }

}
