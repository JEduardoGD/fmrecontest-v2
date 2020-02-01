package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;

@Repository
public interface IContestQsoRepository extends JpaRepository<ContestQso, Long> {
	public List<ContestQso> findByContestLog(ContestLog contestLog);
}
