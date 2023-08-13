package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    @Query("SELECT pr FROM Professor p JOIN p.colegiados c JOIN c.reunioes r JOIN r.processos pr WHERE p.id = ?")
    public List<Processo> AllProcessosByProfessor(Long idProfessor);

}
