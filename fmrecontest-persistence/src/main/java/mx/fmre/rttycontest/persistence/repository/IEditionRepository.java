package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.Edition;

@Repository
public interface IEditionRepository extends JpaRepository<Edition, Integer> {

	@Query(value = "SELECT E FROM Edition E INNER JOIN Contest C ON (C.id = E.contest.id) WHERE E.contest.id = :contestId")
	public List<Edition> getByContestId(@Param("contestId") Integer contestId);

	@Query(value = "SELECT E FROM Edition E WHERE E.active = true")
	public List<Edition> getActiveEditionOfContest();
}
