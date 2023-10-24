package br.edu.ifpb.agora.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3, max = 40)
    @Pattern(regexp = "[a-zA-ZÀ-ÖØ-öø-ÿ\s]+")
    private String nome;

    @NotBlank(message="Campo obrigatório")
    @Pattern(regexp = "[0-9]{11}")
    private String fone;
    
    @NotBlank(message="Campo obrigatório")
    @Pattern(regexp = "[0-9]{11}")
    private String matricula;
    
    @NotBlank(message="Campo obrigatório")
    @Email
    private String login;

    @NotBlank(message="Campo obrigatório")
    @Size(min = 3, max = 60)
    private String senha;

    private boolean admin;
    @ManyToOne
    @JoinColumn(name="curso_id")
    private Curso curso;

}
