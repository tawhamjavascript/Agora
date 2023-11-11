package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("cursoRepository")
public interface CursoRepository extends JpaRepository<Curso, Long> {
}




