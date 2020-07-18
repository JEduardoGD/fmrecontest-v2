package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.RelQsoConteo;

@Repository
public interface IRelQsoConteoRepository extends JpaRepository<RelQsoConteo, Long> {
	public RelQsoConteo findByContestQsoAndConteo(ContestQso contestQso, Conteo conteo);
}
