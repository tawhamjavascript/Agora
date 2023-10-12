package br.edu.ifpb.agora.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Colegiado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date dataInicio;
    private Date dataFim;
    private String descricao;
    private String portaria;

    @OneToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @ManyToMany(mappedBy = "colegiados")
    private List<Professor> membros;

    @OneToMany(mappedBy = "colegiado")
    private List<Reuniao> reunioes;

    public void addReuniao(Reuniao reuniao) {
        this.reunioes.add(reuniao);
    }
}
