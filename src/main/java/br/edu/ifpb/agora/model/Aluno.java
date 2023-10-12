package br.edu.ifpb.agora.model;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data

@NoArgsConstructor
@Entity


public class Aluno extends Usuario {

}
