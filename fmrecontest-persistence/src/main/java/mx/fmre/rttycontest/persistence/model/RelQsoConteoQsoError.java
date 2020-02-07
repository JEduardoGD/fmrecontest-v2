package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.util.Date;

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

@Entity
@Data
@Table(name = "REL_QSO_CONTEO_QSO_ERROR")
public class RelQsoConteoQsoError implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1601409593728534496L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_REL_QSO_CONTEO_QSO_ERROR")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_REL_QSO_CONTEO")
	private RelQsoConteo relQsoConteo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_QSO_ERROR")
	private CatQsoError catQsoError;
	
	@Column(name = "D_DATETIME")
	private Date datetime;
}
