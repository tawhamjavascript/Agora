package br.edu.ifpb.agora.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Reuniao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date dataReuniao;

    @Enumerated(EnumType.ORDINAL)
    private StatusReuniao status;

    private byte[] ata;

    @OneToMany()
    @JoinColumn(name = "reuniao_id")
    private List<Processo> processos;

    @ManyToOne
    private Colegiado colegiado;

    public Reuniao() {
    }
    public Reuniao(Date dataReuniao, StatusReuniao status, Colegiado colegiado) {
        this.dataReuniao = dataReuniao;
        this.status = status;
        this.colegiado = colegiado;
    }

    public long getId() {
        return id;
    }

    public Date getDataReuniao() {
        return dataReuniao;
    }

    public StatusReuniao getStatus() {
        return status;
    }

    public void setStatus(StatusReuniao status) {
        this.status = status;
    }

    public byte[] getAta() {
        return ata;
    }

    public void setAta(byte[] ata) {
        this.ata = ata;
    }

    public void addProcesso(Processo processo) {
        processos.add(processo);
    }

    public List<Processo> getProcessos() {
        return processos;
    }

    public Colegiado getColegiado() {
        return colegiado;
    }

}
