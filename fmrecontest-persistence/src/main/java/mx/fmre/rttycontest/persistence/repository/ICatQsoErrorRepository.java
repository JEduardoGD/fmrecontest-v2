package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.CatQsoError;
import mx.fmre.rttycontest.persistence.model.Edition;

@Repository
public interface ICatQsoErrorRepository extends JpaRepository<CatQsoError, Integer> {
	public List<CatQsoError> findByEdition(Edition edition);
}
