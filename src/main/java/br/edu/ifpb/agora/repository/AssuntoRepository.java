package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AssuntoRepository extends JpaRepository<Assunto, Long> {
}
