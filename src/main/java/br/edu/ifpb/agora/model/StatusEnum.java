package br.edu.ifpb.agora.model;

public enum StatusEnum {
    CRIADO("Criado"), DISTRIBUIDO("Distribuido"), EM_PAUTA("Em pauta"), EM_JULGAMENTO("Em julgamento"), JULGADO("Julgado");

    private String status;

    StatusEnum(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
