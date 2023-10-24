package br.edu.ifpb.agora.model;

public enum StatusReuniao {
    ENCERRADA("encerrada"), PROGRAMADA("programada"), EM_ANDAMENTO("em andamento"), SEM_FILTRO("SEM FILTRO");

    private String status;

    StatusReuniao(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
