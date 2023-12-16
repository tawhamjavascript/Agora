package br.edu.ifpb.agora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.agora.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    
    
}
