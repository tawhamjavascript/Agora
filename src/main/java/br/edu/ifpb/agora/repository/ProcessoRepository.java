package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Aluno;
import br.edu.ifpb.agora.model.Assunto;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.StatusEnum;

import org.springframework.data.jpa.repository.JpaRepository;


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




}
