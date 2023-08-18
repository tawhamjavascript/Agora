package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
