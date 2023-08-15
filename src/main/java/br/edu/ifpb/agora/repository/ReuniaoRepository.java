package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Reuniao;
import br.edu.ifpb.agora.model.StatusReuniao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {
    @Query("SELECT r FROM Professor p JOIN p.colegiados c JOIN c.reunioes r WHERE p.id = ?")
    public List<Reuniao> AllReunioesByProfessor(Long idProfessor);

    @Query("select r from Professor p join p.colegiados c join c.reunioes r where p.id = ? and c.id = ?")
    public List<Reuniao> AllReunioesByProfessorAndColegiado(Long idProfessor, Long idColegiado);

    @Query("select r from Professor p join p.colegiados c join c.reunioes r where p.id = ? and c.id = ? and r.status = ?")
    public List<Reuniao> AllReunioesByProfessorAndColegiadoAndStatus(Long idProfessor, Long idColegiado, StatusReuniao status);




}
