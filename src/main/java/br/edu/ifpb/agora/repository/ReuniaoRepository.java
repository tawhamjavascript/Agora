package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Reuniao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {
    @Query("SELECT r FROM Professor p JOIN p.colegiados c JOIN c.reunioes r WHERE p.id = ?")
    public List<Reuniao> AllReunioesByProfessor(Long idProfessor);
}
