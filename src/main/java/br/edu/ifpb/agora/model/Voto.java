package br.edu.ifpb.agora.model;

public class Voto {
    private long id;
    private TipoVoto tipoVoto;
    private boolean ausente;

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
