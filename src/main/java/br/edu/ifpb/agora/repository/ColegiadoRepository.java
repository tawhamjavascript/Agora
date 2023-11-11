package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Colegiado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("colegiadoRepository")
public interface ColegiadoRepository extends JpaRepository<Colegiado, Long> {
    Colegiado findByCursoId(Long id);
}
