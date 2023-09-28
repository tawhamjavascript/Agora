package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Reuniao;
import br.edu.ifpb.agora.model.StatusReuniao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {

    @Query("select r from Professor p join p.colegiados c join c.reunioes r where p.id = ?1 and r.status = ?2")
    public List<Reuniao> AllReunioesByProfessorAndColegiadoAndStatus(Long idProfessor, StatusReuniao status);

    Reuniao findByStatus(StatusReuniao statusReuniao);
}
