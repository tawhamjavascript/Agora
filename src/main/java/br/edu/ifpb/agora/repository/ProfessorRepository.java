package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Professor findByCoordenadorTrueAndCursoId(Long id);



    Professor findByMatricula(String matricula);

    List<Professor> findAllByCursoId(Long id);
}
