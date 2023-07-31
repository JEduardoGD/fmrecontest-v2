package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.fmre.rttycontest.persistence.model.RelExternallogEdition;
import mx.fmre.rttycontest.persistence.model.RelExternallogEditionPk;

public interface IRelExternallogEditionRepository
        extends JpaRepository<RelExternallogEdition, RelExternallogEditionPk> {

}
