package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
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

@Data
@Entity
@Table(name = "CAT_QSO_ERROR")
public class CatQsoError implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 6243220261065288502L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "N_ID_QSO_ERROR")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_EDITION")
	private Edition edition;

	@Column(name = "S_KEY")
	private String key;

	@Column(name = "S_DESCRIPCION")
	private String description;

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "relQsoConteo", fetch = FetchType.LAZY)
	private List<RelQsoConteoQsoError> catQsoError;
}
