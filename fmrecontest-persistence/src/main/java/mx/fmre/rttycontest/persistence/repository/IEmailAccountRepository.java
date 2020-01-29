package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.Contest;
import mx.fmre.rttycontest.persistence.model.EmailAccount;

@Repository
public interface IEmailAccountRepository extends JpaRepository<EmailAccount, Integer>{
	public EmailAccount findByContest(Contest Contest);
}
