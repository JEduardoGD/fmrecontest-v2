package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.RelConteoContestLog;

@Repository
public interface IRelConteoContestLogRepository extends JpaRepository<RelConteoContestLog, Integer>{

}
