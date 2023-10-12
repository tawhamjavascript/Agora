package br.edu.ifpb.agora.model;

import jakarta.persistence.*;
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
    private Date dataReuniao;

    @Enumerated(EnumType.ORDINAL)
    private StatusReuniao status;

    private byte[] ata;

    @OneToMany()
    @JoinColumn(name = "reuniao_id")
    private List<Processo> processos;

    @ManyToOne
    private Colegiado colegiado;


    public Reuniao(Date data, StatusReuniao statusReuniao, Colegiado colegiado) {
        this.dataReuniao = data;
        this.status = statusReuniao;
        this.colegiado = colegiado;
        this.processos = new ArrayList<>();
    }

    public void addProcesso(Processo processo) {
        this.processos.add(processo);

    }
}
