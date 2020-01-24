package mx.fmre.rttycontest.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.AttachedFile;
import mx.fmre.rttycontest.persistence.model.Email;

@Repository
public interface IAttachedFileRepository extends JpaRepository<AttachedFile, Integer> {
	public List<AttachedFile> findByEmail(Email email);
}
