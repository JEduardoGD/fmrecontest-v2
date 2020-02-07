package mx.fmre.rttycontest.persistence.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.CatFrequencyBand;

@Repository
public interface ICatFrequencyBandRepository extends JpaRepository<CatFrequencyBand, Integer> {
	
	@Query(value = "" +
	        "select fb " +
			"from CatFrequencyBand fb " +
	        "where fb.startFrequency <= :frequency and :frequency <= fb.endFrequency ")
	public List<CatFrequencyBand> getByFrequency(@Param("frequency") BigDecimal frequency);
	
	@Query(value = "" +
	        "select max(fb) " +
			"from CatFrequencyBand fb " +
	        "where fb.startFrequency >= :frequencyStart and " +
	        "      fb.endFrequency <= :frequencyEnd ")
	public List<CatFrequencyBand> getAllbandsOnFrecuencyRange(
			@Param("frequencyStart")BigDecimal frequencyStart,
			@Param("frequencyEnd")BigDecimal frequencyEnd);
	
	public List<CatFrequencyBand> findByBand(@Param("band")String band);
	
	@Query(value = "" +
	        "select fb " +
			"from CatFrequencyBand fb " +
	        "where fb.band in :bands ")
	public List<CatFrequencyBand> findByBands(@Param("bands")List<String> bands);
}
