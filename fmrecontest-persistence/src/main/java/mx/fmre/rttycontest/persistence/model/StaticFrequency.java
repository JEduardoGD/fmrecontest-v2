package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "CAT_STATIC_FREQUENCY")
@ToString
public class StaticFrequency implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3785624027359563593L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "N_ID_STATIC_FREQUENCY")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_BAND")
	private CatBand band;
	
	@Column(name = "N_FREQUENCY")
	private BigDecimal frequency;
}
