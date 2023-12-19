package br.edu.ifpb.agora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reuniao implements EntidadesSalvarDocumento{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @FutureOrPresent(message = "A data da reunião deve ser no presente ou no futuro")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dataReuniao;

    @NotBlank(message = "É necessário a definição do horário da reunião")
    private String horario;

    @Enumerated(EnumType.ORDINAL)
    private StatusReuniao status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reuniao_id")
    private List<Processo> processos;

    @ManyToOne
    private Colegiado colegiado;

    @OneToOne
    private Documento ata;

    public void addProcesso(Processo processo) {
        this.processos.add(processo);

    }
}
