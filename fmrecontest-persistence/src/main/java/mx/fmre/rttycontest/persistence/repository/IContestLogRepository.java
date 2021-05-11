package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;

@Repository
public interface IContestLogRepository extends JpaRepository<ContestLog, Long> {
	
	public ContestLog findByEmail(Email email);
	
	@Query(value = "" +
			"select LOG " +
			"from ContestLog LOG " +
			"JOIN Email E ON LOG.email.id = E.id " +
			"WHERE E.edition = :edition and " +
			"      LOG.dxccEntity is null and " +
			"      (LOG.dxccNotFound is null or LOG.dxccNotFound = 0) ")
	public List<ContestLog> getContestLogWithoutDxccEntityByEdition(@Param("edition") Edition edition);
	
	@Query(value = "" +
			"select LOG " +
			"from ContestLog LOG " +
			"JOIN Email E ON LOG.email.id = E.id " +
			"WHERE E.edition = :edition ")
	public List<ContestLog> findByEdition(@Param("edition") Edition edition);
	
	@Query(value = "" +
			"select LOG " +
			"from ContestLog LOG " +
			"JOIN RelConteoContestLog R ON R.contestLog.id = LOG.id " +
			"WHERE R.conteo = :conteo ")
	public List<ContestLog> findByConteo(@Param("conteo") Conteo conteo);
    
	@Query(value = "" +
            "select LOG " +
            "from ContestLog LOG " +
            "JOIN Email E ON LOG.email.id = E.id " +
            "WHERE E.edition = :edition and " +
            "      LOG.callsign like :callsign")
    public List<ContestLog> findByEditionAndCallsign(@Param("edition") Edition edition, @Param("callsign") String callsign);
    
    @Query(value = "" +
            "select max(LOG.group) " +
            "from ContestLog LOG ")
    public Long getMaxGroup();
}

