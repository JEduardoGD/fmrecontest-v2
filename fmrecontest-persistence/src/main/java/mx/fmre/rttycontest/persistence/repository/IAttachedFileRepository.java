package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.AttachedFile;

@Repository
public interface IAttachedFileRepository extends JpaRepository<AttachedFile, Integer> {

}
