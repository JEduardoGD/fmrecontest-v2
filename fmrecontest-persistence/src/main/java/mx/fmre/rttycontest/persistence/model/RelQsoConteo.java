package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "TBL_QSO_CONTEO")
public class RelQsoConteo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -759147586979300241L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "N_ID_REL_QSO_CONTEO")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_CONTEST_QSO")
	private ContestQso contestQso;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_CONTEO")
	private Conteo conteo;
	
	@Column(name = "D_DATETIME")
	private Date datetime;
	
	@Column(name = "N_POINTS")
	private Integer points;
	
	@Column(name = "IS_COMPLETE")
	private boolean complete;
	
	@Column(name = "N_IS_MULTIPLY")
	private boolean multiply;
	
	@Column(name = "N_IS_DXCC_OR_BAND_ERROR")
	private Boolean dxccOrBandError;
	
	

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "relQsoConteo", fetch = FetchType.LAZY)
	private List<RelQsoConteoQsoError> relQsoConteoQsoErrors;
}
