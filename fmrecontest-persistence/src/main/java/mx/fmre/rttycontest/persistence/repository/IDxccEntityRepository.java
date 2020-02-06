package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.dxcc.dao.DxccEntityCallsignDAO;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.Edition;

@Repository
public interface IDxccEntityRepository extends JpaRepository<DxccEntity, Long> {
	@Query( value = "" +
			"select new mx.fmre.rttycontest.persistence.dxcc.dao.DxccEntityCallsignDAO(E, qso) " +
			"from DxccEntity E " +
			"join ContestQso qso on qso.dxccEntity.id = E.id " +
			"join ContestLog log on log.id = qso.contestLog.id " +
			"join Email email on email.id = log.email.id " +
			"join Edition edition on edition.id = email.edition.id " +
			"where edition = :edition " +
			"group by E, qso, log, email, edition ")
	public List<DxccEntityCallsignDAO> getAllByEdition(@Param("edition") Edition edition);
	
	@Query( value = "" +
			"select E from DxccEntity E " +
			"join ContestQso qso on qso.dxccEntity.id = E.id " +
			"join ContestLog log on log.id = qso.contestLog.id " +
			"join Email email on email.id = log.email.id " +
			"join Edition edition on edition.id = email.edition.id " +
			"where qso.callsignr = :callsign AND edition = :edition")
	public List<DxccEntity> getByCallsignOnEdition(
			@Param("callsign") String callsign, 
			@Param("edition") Edition edition);
}
