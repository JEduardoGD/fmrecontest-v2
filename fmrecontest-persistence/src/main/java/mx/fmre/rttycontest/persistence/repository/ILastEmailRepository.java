package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.LastEmail;

@Repository
public interface ILastEmailRepository extends JpaRepository<LastEmail, Integer> {
	public List<LastEmail>findByEditionId(Integer editionId);
	
	public List<LastEmail>findByEmailSubjectAndEditionId(String subject, Integer editionId);
}
