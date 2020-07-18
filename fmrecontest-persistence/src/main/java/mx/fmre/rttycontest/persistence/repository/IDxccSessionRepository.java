package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.DxccSession;

@Repository
public interface IDxccSessionRepository extends JpaRepository<DxccSession, Integer> {
	@Query(value="" +
			"select session " +
			"from DxccSession session " +
			"where session.gmTime <= CURRENT_TIMESTAMP and" +
			"      session.key != null and " +
			"      session.error = null ")
	public List<DxccSession> getActiveSessions();
}
