package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    public Professor findByCoordenadorTrue();
}
