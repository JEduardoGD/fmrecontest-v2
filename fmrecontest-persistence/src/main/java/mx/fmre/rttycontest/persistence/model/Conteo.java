package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
@Data
@Entity
@Table(name = "TBL_CONTEO")
public class Conteo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3944540941363614304L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "N_ID_CONTEO")
	private Integer id;
	
	@Column(name = "S_DESCRIPTION")
	private String description;
	
	@Column(name = "S_DATETIME")
	private Date datetime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_EDITION")
	private Edition edition;

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "conteo", fetch = FetchType.LAZY)
	private List<RelQsoConteo> relQsoConteos;

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "conteo", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<RelConteoContestLog> relConteoContestLogs;
}
