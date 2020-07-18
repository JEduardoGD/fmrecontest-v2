package mx.fmre.rttycontest.persistence.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "V_BAND_FREQUENCY")
public class VBandFrequency {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "N_ID_BAND")
	private Integer id;
	
	@Column(name = "S_BAND")
	private String band;
	
	@Column(name = "MIN")
	private BigDecimal startFrequency;
	
	@Column(name = "MAX")
	private BigDecimal endFrequency;
}
