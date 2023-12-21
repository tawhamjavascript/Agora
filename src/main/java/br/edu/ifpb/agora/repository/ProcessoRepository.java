package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Aluno;
import br.edu.ifpb.agora.model.Documento;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.Professor;
import br.edu.ifpb.agora.model.StatusEnum;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    // consultas JPA atualizadas com spring security

    public List<Processo> findAllByInteressadoMatricula(String matricula);

    public Page<Processo> findAllByInteressadoMatricula(String matricula, Pageable paging);

    public Page<Processo> findAllByInteressadoId(Long id, Pageable p);

    public List<Processo> findAllByInteressadoMatriculaAndAssuntoId(String matricula, Long idAssunto);

    public List<Processo> findAllByInteressadoMatriculaAndStatus(String matricula, StatusEnum status);

    public List<Processo> findAllByInteressadoMatriculaAndAssuntoIdOrderByDataRecepcaoDesc(String matricula, Long idAssunto);

    public List<Processo> findAllByInteressadoMatriculaAndStatusOrderByDataRecepcaoDesc(String matricula, StatusEnum status);

    public List<Processo> findAllByInteressadoMatriculaOrderByDataRecepcaoDesc(String matricula);

    public List<Processo> findAllByRelatorMatricula(String matricula);

    public List<Processo> findAllByStatusAndCursoIdAndDecisaoRelatorIsNotNull(StatusEnum status, Long id);

    // termina aqui consultas JPA atualizadas com spring security




    public List<Processo> findAllByRelatorId(Long idRelator);

    public List<Processo> findAllByEmPauta(Boolean pauta);

    public List<Processo> findAllByInteressadoIdAndStatus(Long id, StatusEnum status);
    
    public Processo findByNumero(String numero);
    
    
        public List<Processo> findAllByInteressadoId(Long id);

    @Query("select p from Colegiado c join c.reunioes r join r.processos p where c.id = ?1")
    public List<Processo> findAllByColegiado(Long id);

    @Query("select p from Colegiado c join c.reunioes r join r.processos p where c.id = ?1 and p.status = ?2")
    public List<Processo> findAllByColegiadoAndStatus(Long id, StatusEnum status);

    @Query("select p from Colegiado c join c.reunioes r join r.processos p where c.id = ?1 and p.interessado.id = ?2")
    public List<Processo> findAllByColegiadoAndInteressado(Long id, Long idInteressado);

    @Query("select p from Colegiado c join c.reunioes r join r.processos p where c.id = ?1 and p.relator.id = ?2")
    public List<Processo> findAllByColegiadoAndRelator(Long id, Long idRelator);
    
    List<Processo> findAllByCursoId(Long id);
    
    List<Processo> findAllByCursoIdAndStatus(Long id, StatusEnum filtroEnum);

    Page<Processo> findAllByCursoId(Long id, Pageable paging);
    
    Page<Processo> findAllByRelatorId(Long id, Pageable paging);
}
