package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Aluno;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.Professor;
import br.edu.ifpb.agora.model.StatusEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    public List<Processo> findAllByInteressadoId(Long id);

    public List<Processo> findAllByInteressadoIdAndAssuntoNome(Long id, String nome);

    public List<Processo> findAllByInteressadoIdAndEmPauta(Long id, Boolean pauta);



    public List<Processo> findAllByRelatorId(Long idRelator);

    public List<Processo> findAllByEmPauta(Boolean pauta);

    public List<Processo> findAllByInteressadoIdAndStatus(Long id, StatusEnum status);

    public Processo findByNumero(String numero);


    @Query("select p from Colegiado c join c.reunioes r join r.processos p where c.id = ?1")
    public List<Processo> findAllByColegiado(Long id);

    @Query("select p from Colegiado c join c.reunioes r join r.processos p where c.id = ?1 and p.status = ?2")
    public List<Processo> findAllByColegiadoAndStatus(Long id, StatusEnum status);

    @Query("select p from Colegiado c join c.reunioes r join r.processos p where c.id = ?1 and p.interessado.id = ?2")
    public List<Processo> findAllByColegiadoAndInteressado(Long id, Long idInteressado);

    @Query("select p from Colegiado c join c.reunioes r join r.processos p where c.id = ?1 and p.relator.id = ?2")
    public List<Processo> findAllByColegiadoAndRelator(Long id, Long idRelator);


    List<Processo> findAllByInteressadoIdAndAssuntoNomeOrderByDataRecepcaoDesc(Long id, String nome);

    List<Processo> findAllByInteressadoIdAndStatusOrderByDataRecepcaoDesc(Long id, StatusEnum status);

    List<Processo> findAllByInteressadoIdOrderByDataRecepcaoDesc(Long id);
}
