package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Reuniao;
import br.edu.ifpb.agora.model.StatusReuniao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reuniaoRepository")
public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {

    @Query("select r from Professor p join p.colegiado c join c.reunioes r where p.id = ?1 and r.status = ?2")
    public List<Reuniao> AllReunioesByProfessorAndColegiadoAndStatus(Long idProfessor, StatusReuniao status);

    @Query("select r from Professor p join p.colegiado c join c.reunioes r where p.id= ?1")
    public List<Reuniao> AllReunioesByProfessorAndColegiado(Long idProfessor);

    Reuniao findByStatus(StatusReuniao statusReuniao);
}
