package br.edu.ifpb.agora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Reuniao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @FutureOrPresent(message = "A data da reunião deve ser no presente ou no futuro")
    private Date dataReuniao;

    @Enumerated(EnumType.ORDINAL)
    private StatusReuniao status;

    private byte[] ata;

    @OneToMany()
    @JoinColumn(name = "reuniao_id")
    private List<Processo> processos;

    @ManyToOne
    private Colegiado colegiado;

    public void addProcesso(Processo processo) {
        this.processos.add(processo);

    }
}
