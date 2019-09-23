package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}