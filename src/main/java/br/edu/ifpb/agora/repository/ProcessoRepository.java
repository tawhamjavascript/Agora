package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Aluno;
import br.edu.ifpb.agora.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    @Query("SELECT pr FROM Professor p JOIN p.colegiados c JOIN c.reunioes r JOIN r.processos pr WHERE p.id = ?")
    public List<Processo> AllProcessosByProfessor(Long idProfessor);

    @Query("SELECT pr.interessado from Processo pr where pr.interessado = ?")
    public List<Processo> AllProcessByAluno(Aluno aluno);

    @Query("select pr from Processo pr where pr.interessado = ? and pr.assunto.nome = ?")
    public List<Processo> AllProcessByAlunoPerSubject(Aluno aluno, String subject);

    @Query("select pr from Processo pr where pr.interessado = ? and pr.emPauta = ?")
    public List<Processo> AllProcessByAlunoPerStatus(Aluno aluno, Boolean pauta);

    @Query("select pr from Processo pr where pr.interessado = ? and pr.assunto.nome = ? order by pr.dataRecepcao ASC")
    public List<Processo> AllProcessByAlunoPerSubjectOrderByDate(Aluno aluno, String subject);

    @Query("select pr from Processo pr where pr.interessado = ? and pr.emPauta = ? order by pr.dataRecepcao ASC")
    public List<Processo> AllProcessByAlunoPerStatusOrderByDate(Aluno aluno, Boolean pauta);

    @Query("select pr from Coordenador c join c.colegiados co join co.reunioes r join r.processos pr where c.id = ? and co.id = ?")
    public List<Processo> AllProcessByCoordenador(Long idCoordenador, Long idColegiado);

    @Query("select pr from Coordenador c join c.colegiados co join co.reunioes r join r.processos pr where c.id = ? and co.id = ? and pr.interessado.nome = ?")
    public List<Processo> AllProcessByCoordenadorPerAluno(Long idCoordenador, Long idColegiado, String aluno);

    @Query("select pr from Coordenador c join c.colegiados co join co.reunioes r join r.processos pr where c.id = ? and co.id = ? and pr.emPauta = ?")
    public List<Processo> AllProcessByCoordenadorPerStatus(Long idCoordenador, Long idColegiado, Boolean pauta);












}
