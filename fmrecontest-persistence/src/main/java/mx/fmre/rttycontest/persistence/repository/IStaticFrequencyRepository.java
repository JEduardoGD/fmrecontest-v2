package mx.fmre.rttycontest.persistence.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.StaticFrequency;

@Repository
public interface IStaticFrequencyRepository extends JpaRepository<StaticFrequency, Long> {
	public StaticFrequency findByFrequency(BigDecimal frequency);
}
