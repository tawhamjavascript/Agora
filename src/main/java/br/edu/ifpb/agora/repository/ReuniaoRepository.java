package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Reuniao;
import br.edu.ifpb.agora.model.StatusReuniao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {

    @Query("select r from Professor p join p.colegiado c join c.reunioes r where p.id = ?1 and r.status = ?2")
    public List<Reuniao> AllReunioesByProfessorAndColegiadoAndStatus(Long idProfessor, StatusReuniao status);

    @Query("select r from Professor p join p.colegiado c join c.reunioes r where p.id= ?1")
    public List<Reuniao> AllReunioesByProfessorAndColegiado(Long idProfessor);

    public List<Reuniao> findByColegiadoId(Long id);

    public List<Reuniao> findByColegiadoIdAndStatus(Long id, StatusReuniao status);

    Reuniao findByStatus(StatusReuniao statusReuniao);

    @Query("select r from Professor p join p.colegiado c join c.reunioes r where p.id= ?1")
    public Page<Reuniao> AllReunioesByProfessorAndColegiado(Long id, Pageable paging);
}
