package br.edu.ifpb.agora.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.agora.model.Authority;
import br.edu.ifpb.agora.model.Authority.AuthorityId;

public interface AuthorityRepository extends JpaRepository<Authority, AuthorityId> {

    
}
