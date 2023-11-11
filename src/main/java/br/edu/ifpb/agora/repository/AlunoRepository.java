package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("alunoRepository")
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("select distinct p.interessado from Processo p join p.curso c where c.id = ?1")
    List<Aluno> findAllByAlunoAndProcessDistinct(Long id);

    Aluno findByMatricula(String matricula);
}
