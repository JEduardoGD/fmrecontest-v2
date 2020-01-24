package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.CatEmailError;
import mx.fmre.rttycontest.persistence.model.Edition;

@Repository
public interface ICatEmailErrorRepository extends JpaRepository<CatEmailError, Integer> {
	public List<CatEmailError> findByEdition(Edition edition);
	
	public CatEmailError findByEditionAndDescripcion(Edition edition, String descripcion);
}
