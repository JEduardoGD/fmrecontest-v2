package mx.fmre.rttycontest.persistence.repository;

import java.util.Date;
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

	@Query(value = "" +
			"SELECT E " +
			"FROM Email E " +
			"WHERE upper(E.subject) = upper(:subject) and " +
			"            E.edition = :edition and " +
			"            E.sentDate <= :d ")
	public List<Email> getAllBySubjectAndEditionBeforeDate(
			@Param("subject") String subject, 
			@Param("edition") Edition edition,
			@Param("d") Date d);
	
	@Query(value = "" +
			"SELECT DISTINCT(E) " +
			"FROM ContestQso QSO " +
			"JOIN ContestLog LOG on QSO.contestLog.id = LOG.id " +
			"JOIN Email E ON LOG.email.id = E.id " +
			"JOIN AttachedFile AF on AF.email.id = E.id " +
			"WHERE E.edition like :edition and " +
			"      AF.isLogFile = true and " +
			"      QSO.dxccEntity IS NULL and " +
			"      (QSO.dxccNotFound is null or QSO.dxccNotFound = 0) ")
	public List<Email>getAllWithLogfileByEditionWithoutDxcc(@Param("edition") Edition edition);
	
	@Query(value = "" +
			"SELECT DISTINCT(E) " +
			"FROM ContestQso QSO " +
			"JOIN ContestLog LOG on QSO.contestLog.id = LOG.id " +
			"JOIN Email E ON LOG.email.id = E.id " +
			"JOIN AttachedFile AF on AF.email.id = E.id " +
			"WHERE E.edition = :edition and " +
			"      AF.isLogFile = true and " +
			"      QSO.dxccEntity IS NULL and " +
			"      QSO.dxccNotFound = 1 ")
	public List<Email>getAllWithLogfileByEditionDxccNotFound(@Param("edition") Edition edition);
	
	public List<Email> findByEditionAndSubject(Edition edition, String subject);
	
    /**
     * obtener todos los Email que contienen QSOS a lo que les falta la entidad
     * DXCC, estos no han sido marcados como erroresy el email esta en estatus 5 (parseado el
     * log y los qsos) por edition
     * 
     * @param edition
     * @return email
     */
	@Query(value = "" +
	        "SELECT EMAIL.* FROM TBL_EMAIL EMAIL " +
	        "INNER JOIN TBL_EDITION EDITION ON EDITION.N_ID_EDITION = EMAIL.N_ID_EDITION " +
	        "INNER JOIN TBL_CONTEST_LOG LOG ON LOG.N_ID_EMAIL = EMAIL.N_ID_EMAIL " +
	        "WHERE 1 = 1 " +
	        "AND EMAIL.N_ID_EMAIL_STATUS = 5 " +
	        "AND EDITION.N_ID_EDITION = :edition " +
	        "GROUP BY EMAIL.N_ID_EMAIL, EMAIL.N_EMAIL_COUNT, EMAIL.N_ID_EDITION, EMAIL.S_RECIPIENTS_FROM_NAME, " +
	        "EMAIL.S_RECIPIENTS_FROM_ADDRESS, EMAIL.D_RECEIVED_DATE, EMAIL.S_RECIPIENTS_TO, EMAIL.D_SENT_DATE, " +
	        "EMAIL.S_SUBJECT, EMAIL.N_ID_EMAIL_STATUS, EMAIL.VERIFIED_AT, EMAIL.ANSWERED_AT ", nativeQuery = true )
    public List<Email> specialQuery(@Param("edition") Edition edition);
    
    /**
     * Obtener los emails que tienen errores
     * 
     * @param edition
     * @return email
     */
    @Query(value = "" +
            "SELECT " +
            "   te.N_ID_EMAIL, " +
            "   te.N_EMAIL_COUNT, " +
            "   te.N_ID_EDITION, " +
            "   te.S_RECIPIENTS_FROM_NAME, " +
            "   te.S_RECIPIENTS_FROM_ADDRESS, " +
            "   te.D_RECEIVED_DATE, " +
            "   te.S_RECIPIENTS_TO, " +
            "   te.D_SENT_DATE, " +
            "   te.S_SUBJECT, " +
            "   te.N_ID_EMAIL_STATUS, " +
            "   te.VERIFIED_AT, " +
            "   te.ANSWERED_AT " +
            "FROM " +
            "   TBL_EMAIL te " +
            "INNER JOIN REL_EMAIL_EMAIL_ERROR REEE ON " +
            "   REEE.N_ID_EMAIL = te.N_ID_EMAIL " +
            "LEFT JOIN V_LAST_EMAIL V ON " +
            "   V.EMAIL_SUBJECT = te.S_SUBJECT " +
            "WHERE  1 = 1" +
            //" and N_ID_EDITION = 2 " +
            "   and V.EMAIL_SUBJECT is null " +
            "   and te.N_ID_EDITION = :N_ID_EDITION " +
            "GROUP BY " +
            "   te.N_ID_EMAIL, " +
            "   te.N_EMAIL_COUNT, " +
            "   te.N_ID_EDITION, " +
            "   te.S_RECIPIENTS_FROM_NAME, " +
            "   te.S_RECIPIENTS_FROM_ADDRESS, " +
            "   te.D_RECEIVED_DATE, " +
            "   te.S_RECIPIENTS_TO, " +
            "   te.D_SENT_DATE, " +
            "   te.S_SUBJECT, " +
            "   te.N_ID_EMAIL_STATUS, " +
            "   te.VERIFIED_AT, " +
            "   te.ANSWERED_AT ", nativeQuery = true )
    public List<Email> getEmailsWithErroresByEditionId(@Param("N_ID_EDITION") long editionId);
    
    @Query(value = "" +
            "SELECT MAX(EMAIL.N_ID_EMAIL) " +
            "FROM TBL_EMAIL EMAIL " +
            "INNER JOIN TBL_EDITION EDITION ON EDITION.N_ID_EDITION = EMAIL.N_ID_EDITION " +
            "INNER JOIN TBL_CONTEST_LOG LOG ON LOG.N_ID_EMAIL = EMAIL.N_ID_EMAIL " +
            "INNER JOIN TBL_CONTEST_QSO QSO ON QSO.N_ID_CONTEST_LOG = LOG.N_ID_CONTEST_LOG " +
            "WHERE 1 = 1 " +
            "AND LOG.S_CALLSIGN = :callsign " +
            "AND EMAIL.N_ID_EMAIL_STATUS = 5 " +
            "AND EDITION.N_ID_EDITION = :edition ", nativeQuery = true )
    public Integer getMaxByCallsignAndEdition(@Param("callsign") String callsign, @Param("edition") Integer edition);
    
    @Query(value = "" +
            "SELECT DISTINCT(E) " +
            "FROM ContestQso QSO " +
            "JOIN ContestLog LOG on QSO.contestLog.id = LOG.id " +
            "JOIN Email E ON LOG.email.id = E.id " +
            "JOIN AttachedFile AF on AF.email.id = E.id " +
            "WHERE E.edition = :edition and " +
            "      AF.isLogFile = true and " +
            "      QSO.dxccEntity IS NULL and " +
            "      QSO.dxccNotFound = 1 ")
    public List<Email>getAllByCallsign(@Param("edition") Edition edition);
    
    @Query(value = "" +
            "SELECT E " +
            "FROM Email E " +
            "JOIN ContestLog LOG on LOG.email.id = E.id " +
            "WHERE LOG.group = :group")
    public List<Email>getAllByContestogGroup(@Param("group") Long group);
}




