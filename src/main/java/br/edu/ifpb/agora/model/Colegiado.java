package br.edu.ifpb.agora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private Date dataInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private Date dataFim;

    @NotBlank(message = "Campo obrigatório")
    @Max(500)
    private String descricao;

    @NotBlank(message = "Campo obrigatório")
    @Pattern(regexp = "[0-9]")
    private String portaria;

    @OneToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "colegiado")
    private List<Professor> membros;

    @OneToMany(mappedBy = "colegiado")
    private List<Reuniao> reunioes;

    public void addReuniao(Reuniao reuniao) {
        this.reunioes.add(reuniao);
    }
}
