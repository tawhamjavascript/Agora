package br.edu.ifpb.agora.model;


import jakarta.persistence.*;

@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private TipoVoto tipoVoto;
    private boolean ausente;

    @ManyToOne
    private Professor professor;

    private String justificativa;


    public Voto() {
    }


    public Voto( TipoVoto tipoVoto, boolean ausente, String justificativa, Professor professor) {
        this.tipoVoto = tipoVoto;
        this.ausente = ausente;
        this.justificativa = justificativa;
        this.professor = professor;
    }

    public Long getId() {
        return id;
    }

    public TipoVoto getTipoVoto() {
        return tipoVoto;
    }

    public boolean isAusente() {
        return ausente;
    }

    public Professor getProfessor() {
        return professor;
    }

    public String getJustificativa() {
        return justificativa;
    }
}
