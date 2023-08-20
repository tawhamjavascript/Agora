package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Aluno;
import br.edu.ifpb.agora.model.Assunto;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.StatusEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    public List<Processo> findAllByInteressado(Aluno aluno);

    public List<Processo> findAllByInteressadoIdAndAssuntoNome(Long id, String nome);

    public List<Processo> findAllByInteressadoIdAndEmPauta(Long id, Boolean pauta);

    @Query("select pr from Processo pr where pr.interessado = ?1 and pr.assunto.nome = ?2 order by pr.dataRecepcao ASC")
    public List<Processo> AllProcessByAlunoPerSubjectOrderByDate(Aluno aluno, String subject);

    public List<Processo> findAllByInteressadoAndEmPautaOrderByDataRecepcaoAsc(Aluno aluno, Boolean pauta);

    public List<Processo> findAllByRelatorId(Long idRelator);

    public List<Processo> findAllByEmPauta(Boolean pauta);

    @Query("select pr from Processo pr where pr.interessado = ?1 and pr.status = ?2")
    public List<Processo> findAllByAlunoAndStatus(Aluno aluno, StatusEnum status);

    @Query("select pr from Processo pr where pr.interessado = ?1 and pr.assunto = ?2")
    public List<Processo> findAllByAlunoAndAssunto(Aluno aluno, Assunto assunto);
}
