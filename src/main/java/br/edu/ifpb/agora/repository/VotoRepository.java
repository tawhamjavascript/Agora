package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {
}
