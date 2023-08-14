package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.EmailEmailError;
import mx.fmre.rttycontest.persistence.model.EmailEmailErrorPk;

@Repository
public interface IEmailEmailErrorRepository extends JpaRepository<EmailEmailError, EmailEmailErrorPk> {
	
	@Query(value ="select eee from EmailEmailError eee where eee.pk.emailId = :emailId")
	public List<EmailEmailError> findByEmailId(@Param("emailId") Integer emailId);
}
