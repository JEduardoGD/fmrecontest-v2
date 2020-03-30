package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.CatBand;

@Repository
public interface ICatBandRepository extends JpaRepository<CatBand, Integer> {
	
}
