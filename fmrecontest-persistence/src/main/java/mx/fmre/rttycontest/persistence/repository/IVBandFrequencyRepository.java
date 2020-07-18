package mx.fmre.rttycontest.persistence.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.VBandFrequency;

@Repository
public interface IVBandFrequencyRepository extends JpaRepository<VBandFrequency, Integer> {
	@Query(value = "" +
			"select v "	+ 
			"from VBandFrequency v " + 
			"where v.startFrequency <= :frequency and " + 
			"            :frequency <= v.endFrequency ")
	public List<VBandFrequency> findByFrequency(@Param("frequency") BigDecimal frequency);
}
