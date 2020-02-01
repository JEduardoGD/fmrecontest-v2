package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.CatEmailError;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;

@Repository
public interface ICatEmailErrorRepository extends JpaRepository<CatEmailError, Integer> {
	public List<CatEmailError> findByEdition(Edition edition);
	
	public CatEmailError findByEditionAndDescripcion(Edition edition, String descripcion);
	
	@Query(value = ""
			+ "select ERROR "
			+ "from CatEmailError ERROR "
			+ "join EmailEmailError EERROR ON EERROR.emailError.id = ERROR.id "
			+ "join Email EMAIL on EERROR.email.id = EMAIL.id "
			+ "WHERE EMAIL = :email")
	public List<CatEmailError> getErrorsOfEmail(@Param("email") Email email);
}
