package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Aluno;
import br.edu.ifpb.agora.model.Colegiado;
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

    public List<Processo> findAllByInteressadoAndAssuntoNomeOrderByDataRecepcaoAsc(Aluno aluno, String assunto);

    public List<Processo> findAllByInteressadoAndEmPautaOrderByDataRecepcaoAsc(Aluno aluno, Boolean pauta);

    public List<Processo> findAllByRelatorId(Long idRelator);

    public List<Processo> findAllByEmPauta(Boolean pauta);

    public List<Processo> findAllByInteressadoIdAndStatus(Long id, StatusEnum status);

    public Processo findByNumero(String numero);

    @Query("select p from Colegiado c.reunioes r join r.processos p where c.id = ?1")
    public List<Processo> findAllByColegiado(Colegiado colegiado);

    @Query("select p from Colegiado c.reunioes r join r.processos p where c.id = ?1 and p.status = ?2")
    public List<Processo> findAllByColegiadoAndStatus(Colegiado colegiado, StatusEnum status);

    @Query("select p from Colegiado c.reunioes r join r.processos p where c.id = ?1 and p.interessado = ?2")
    public List<Processo> findAllByColegiadoAndInteressado(Colegiado colegiado, Aluno interessado);

    @Query("select p from Colegiado c.reunioes r join r.processos p where c.id = ?1 and p.relator = ?2")
    public List<Processo> findAllByColegiadoAndRelator(Colegiado colegiado, Professor relator);




}
