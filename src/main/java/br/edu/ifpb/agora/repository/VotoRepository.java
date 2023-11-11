package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Voto;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("votoRepository")
public interface VotoRepository extends JpaRepository<Voto, Long> {
}
