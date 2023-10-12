package br.edu.ifpb.agora.model;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Professor extends Usuario{
    
    @ManyToMany
    private List<Colegiado> colegiados;
    private boolean coordenador;

    @OneToMany(mappedBy = "professor")
    private List<Voto> votos;

}
