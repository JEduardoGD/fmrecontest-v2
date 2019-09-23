package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
	Role findByRole(String role);
}
