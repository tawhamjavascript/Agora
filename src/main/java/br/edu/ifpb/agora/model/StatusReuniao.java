package br.edu.ifpb.agora.model;

public enum StatusReuniao {
    ENCERRADA("encerrada"), PROGRAMADA("programada"), EM_ANDAMENTO("em andamento");

    private String status;

    StatusReuniao(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
