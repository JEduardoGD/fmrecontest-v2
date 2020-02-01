package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.fmre.rttycontest.persistence.model.EmailStatus;

public interface IEmailEstatusRepository extends JpaRepository<EmailStatus, Integer> {
	public EmailStatus findByStatus(String status);
}
