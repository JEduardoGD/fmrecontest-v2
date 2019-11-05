package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.UserRole;

@Repository
public interface IRoleRepository extends JpaRepository<UserRole, Integer> {
	UserRole findByRole(String role);
}
