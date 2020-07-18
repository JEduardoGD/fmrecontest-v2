package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.RelQsoConteo;
import mx.fmre.rttycontest.persistence.model.RelQsoConteoQsoError;

@Repository
public interface IRelQsoConteoQsoErrorRepository extends JpaRepository<RelQsoConteoQsoError, Integer> {
	public List<RelQsoConteoQsoError> findByRelQsoConteo(RelQsoConteo relQsoConteo);
}
