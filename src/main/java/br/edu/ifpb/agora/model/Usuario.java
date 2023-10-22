package br.edu.ifpb.agora.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message="Campo obrigatório")
    private String nome;
    private String fone;
    
    @NotBlank(message="Campo obrigatório")
    private String matricula;
    
    @NotBlank(message="Campo obrigatório")
    private String login;

    @NotBlank(message="Campo obrigatório")
    private String senha;
    private boolean admin;
    @ManyToOne
    @JoinColumn(name="curso_id")
    private Curso curso;

}
