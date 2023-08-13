package br.edu.ifpb.agora.repository;

import br.edu.ifpb.agora.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
