package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Email;

@Repository
public interface IContestLogRepository extends JpaRepository<ContestLog, Integer> {
	public ContestLog findByEmail(Email email);
}

