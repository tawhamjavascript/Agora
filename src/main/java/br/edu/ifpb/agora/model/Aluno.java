package br.edu.ifpb.agora.model;


import jakarta.persistence.Entity;

@Entity
public class Aluno extends Usuario {
        public Aluno() {

        }
        public Aluno(String nome, String fone, String matricula, String login, String senha) {
            super(nome, fone, matricula, login, senha, false);
        }
}
