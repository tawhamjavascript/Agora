package br.edu.ifpb.agora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @PastOrPresent(message = "A data de início deve ser a data atual")
    private Date dataInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "A data de fim deve ser a data atual ou posterior")
    private Date dataFim;

    @NotBlank(message = "Campo obrigatório")
    @Size(min = 5, max = 500, message = "O nome deve ter entre 5 e 500 caracteres")
    private String descricao;

    @NotBlank(message = "Campo obrigatório")
    @Pattern(regexp = "[0-9]+", message = "O número da portaria deve conter apenas números")
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
