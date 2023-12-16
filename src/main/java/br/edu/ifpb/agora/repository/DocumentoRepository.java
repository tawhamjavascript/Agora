package br.edu.ifpb.agora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.agora.model.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    
}
