package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;

@Repository
public interface IContestLogRepository extends JpaRepository<ContestLog, Integer> {
	public ContestLog findByEmail(Email email);
	@Query(value = "" +
			"select LOG " +
			"from ContestLog LOG " +
			"JOIN Email E ON LOG.email.id = E.id " +
			"WHERE E.edition = :edition and " +
			"      LOG.dxccEntity is null and " +
			"      (LOG.dxccNotFound is null or LOG.dxccNotFound = 0) ")
	public List<ContestLog> getContestLogWithoutDxccEntityByEdition(@Param("edition") Edition edition);
}

