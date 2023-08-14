package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.ExternalQso;
import mx.fmre.rttycontest.persistence.model.ExternalQsoKey;

@Repository
public interface IExternalQsoRepository extends JpaRepository<ExternalQso, ExternalQsoKey>{
    
}
