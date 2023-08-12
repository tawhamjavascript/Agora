package br.edu.ifpb.agora.model;


import jakarta.persistence.*;

@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.ORDINAL)
    private TipoVoto tipoVoto;
    private boolean ausente;

    @OneToOne
    @JoinColumn(name = "professor_id")
    private Professor voto;


    public Voto() {
    }

    public Voto( TipoVoto tipoVoto, boolean ausente, Professor voto) {
        this.tipoVoto = tipoVoto;
        this.ausente = ausente;
        this.voto = voto;
    }

    public long getId() {
        return id;
    }

    public TipoVoto getTipoVoto() {
        return tipoVoto;
    }

    public boolean isAusente() {
        return ausente;
    }

    public Professor getVoto() {
        return voto;
    }
}
