package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.Email;

@Repository
public interface IContestQsoRepository extends JpaRepository<ContestQso, Long> {
	public List<ContestQso> findByContestLog(ContestLog contestLog);
	
	@Query(value = "" +
            "select QSO " +
            "from ContestQso QSO " +
            "JOIN ContestLog LOG ON QSO.contestLog.id = LOG.id " +
            "WHERE LOG.email = :email ")
	public List<ContestQso> findByEmail(@Param("email") Email email);
}
