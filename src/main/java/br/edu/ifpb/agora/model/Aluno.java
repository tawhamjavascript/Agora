package br.edu.ifpb.agora.model;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Aluno extends Usuario {
    @OneToMany(mappedBy = "interessado")
    private List<Processo> processos;



    public void addProcesso(Processo processo) {
        this.processos.add(processo);
    }

}
