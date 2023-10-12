package br.edu.ifpb.agora.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String numero;
    private Date dataRecepcao;
    private Date dataDistribuicao;
    private Date dataParecer;
    private byte[] parecer;

    private String textoAluno;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Enumerated(EnumType.ORDINAL)
    private TipoDecisao decisaoRelator;

    @OneToOne
    @JoinColumn(name = "assunto_id")
    private Assunto assunto;

    @OneToMany
    @JoinColumn(name = "processo_id")
    private List<Voto> votos;

    @OneToOne
    @JoinColumn(name = "aluno_id")
    private Aluno interessado;

    private boolean emPauta = false;

    @OneToOne
    @JoinColumn(name = "professor_id")
    private Professor relator;

    @ElementCollection
    private List<byte[]> anexos;


    public void addAnexos(byte[] anexo) {
        this.anexos.add(anexo);
    }
}
