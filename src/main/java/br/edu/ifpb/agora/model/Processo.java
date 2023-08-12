package br.edu.ifpb.agora.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String numero;
    private Date dataRecepcao;
    private Date dataDistribuicao;
    private Date dataParecer;
    private byte[] parecer;

    @Enumerated(EnumType.ORDINAL)
    private TipoDecisao decisaoRelator;

    @OneToOne
    @JoinColumn(name = "assunto_id")
    private Assunto assunto;

    @OneToMany
    @JoinColumn(name = "processo_id")
    private List<Voto> votos;

    @OneToOne
    @JoinColumn(name = "aluno_id")
    private Aluno interessado;

    private boolean emPauta = false;

    @OneToOne
    @JoinColumn(name = "professor_id")
    private Professor relator;


    public Processo() {
    }

    public Processo(String numero, Date dataRecepcao, Date dataDistribuicao, Date dataParecer, byte[] parecer, TipoDecisao decisaoRelator, Assunto assunto, Aluno interessado, Professor relator) {

        this.numero = numero;
        this.dataRecepcao = dataRecepcao;
        this.dataDistribuicao = dataDistribuicao;
        this.dataParecer = dataParecer;
        this.parecer = parecer;
        this.decisaoRelator = decisaoRelator;
        this.assunto = assunto;
        this.interessado = interessado;
        this.relator = relator;
    }

    public long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }


    public Date getDataRecepcao() {
        return dataRecepcao;
    }

    public Date getDataDistribuicao() {
        return dataDistribuicao;
    }

    public Date getDataParecer() {
        return dataParecer;
    }

    public byte[] getParecer() {
        return parecer;
    }

    public void setParecer(byte[] parecer) {
        this.parecer = parecer;
    }

    public TipoDecisao getDecisaoRelator() {
        return decisaoRelator;
    }

    public Assunto getAssunto() {
        return assunto;
    }

    public List<Voto> getVotos() {
        return votos;
    }
    public void addVoto(Voto voto) {
        this.votos.add(voto);
    }

    public Aluno getInteressado() {
        return interessado;
    }

    public boolean isEmPauta() {
        return emPauta;
    }
    public void setEmPauta(boolean emPauta) {
        this.emPauta = emPauta;
    }

    public Professor getRelator() {
        return relator;
    }


}
