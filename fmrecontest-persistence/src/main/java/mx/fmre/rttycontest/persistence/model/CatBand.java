package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CAT_BANDS")
public class CatBand implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 6827293241759217393L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "N_ID_BAND")
	private Integer id;
	
	@Column(name = "S_BAND")
	private String band;

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "band", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<CatFrequencyBand> catFrequencyBands;

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "band", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<ContestQso> contestqsos;
	
}
