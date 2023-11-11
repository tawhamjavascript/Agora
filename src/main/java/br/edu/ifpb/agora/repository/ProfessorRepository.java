package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("professorRepository")
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Professor findByCoordenadorTrueAndCursoId(Long id);



    Professor findByMatricula(String matricula);

    List<Professor> findAllByCursoId(Long id);
}
