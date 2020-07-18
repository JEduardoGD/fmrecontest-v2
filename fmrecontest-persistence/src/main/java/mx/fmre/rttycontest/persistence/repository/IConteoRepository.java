package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.Edition;

@Repository
public interface IConteoRepository extends JpaRepository<Conteo, Integer> {
	@Query(value = "" +
			"select max(C) " + 
			"from Conteo C " +
			"where C.edition = :edition ")
	public Conteo getLastForEdition(@Param("edition") Edition edition);
}
