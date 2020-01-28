package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.EmailStatus;

@Repository
public interface IEmailRepository extends JpaRepository<Email, Integer> {
	@Query(value = "" +
			"SELECT E.emailCount " +
			"FROM Email E " +
			"WHERE E.emailCount >= :emailCountStart and " +
			"      E.emailCount <= :emailCountEnd and " +
			"      E.edition.id = :idEdition")
	public List<Integer> getEmailCountsSaved(
			@Param("emailCountStart") Integer emailCountStart,
			@Param("emailCountEnd") Integer emailCountEnd,
			@Param("idEdition") Integer idEdition);
	
	@Query(value = "" +
			"SELECT E.emailCount " +
			"FROM Email E " +
			"WHERE E.emailCount >= :emailCountStart and " +
			"      E.edition.id = :idEdition")
	public List<Integer> getEmailCountsSaved(
			@Param("emailCountStart") Integer emailCountStart,
			@Param("idEdition") Integer idEdition);
	
	public List<Email> findByEdition(Edition edition);

	@Query(value = "" +
			"SELECT E " +
			"FROM Email E " +
			"WHERE E.edition = :edition and " +
			"      E.emailStatus in :statuses and " +
			"      E.verifiedAt IS NULL")
	public List<Email> findByEditionAndEmailStatusesAndNotVerified(
			@Param("edition") Edition edition, 
			@Param("statuses") List<EmailStatus> statuses);

	@Query(value = "" +
			"SELECT E " +
			"FROM Email E " +
			"WHERE E.edition = :edition and " +
			"      E.emailStatus in :statuses and " +
			"      E.verifiedAt IS NOT NULL AND " +
			"      E.answeredAt IS NULL ")
	public List<Email> findByEditionAndEmailStatusesAndVerifiedAndNotAnswered(
			@Param("edition") Edition edition, 
			@Param("statuses") List<EmailStatus> statuses);
}
