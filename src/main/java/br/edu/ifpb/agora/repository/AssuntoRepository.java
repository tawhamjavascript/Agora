package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Aluno;
import br.edu.ifpb.agora.model.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssuntoRepository extends JpaRepository<Assunto, Long> {
    public List<Assunto> findByInteressado(Aluno aluno);
    


}
