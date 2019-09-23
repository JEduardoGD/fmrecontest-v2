package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.Contest;

@Repository
public interface IContestRepository extends JpaRepository<Contest, Integer>{

}
