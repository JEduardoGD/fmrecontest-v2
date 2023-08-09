package mx.fmre.rttycontest.persistence.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.fmre.rttycontest.persistence.model.RelExternallogEdition;
import mx.fmre.rttycontest.persistence.model.RelExternallogEditionPk;

public interface IRelExternallogEditionRepository
        extends JpaRepository<RelExternallogEdition, RelExternallogEditionPk> {
    
    @Query(value = "" +
            "SELECT r " +
            "FROM RelExternallogEdition r " +
            "WHERE r.edition.id = :id")
    public Set<RelExternallogEdition> findByEdition(@Param("id") Integer id);
}
