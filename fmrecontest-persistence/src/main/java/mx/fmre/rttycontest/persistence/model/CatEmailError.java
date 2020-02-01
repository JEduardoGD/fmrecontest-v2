package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;

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

@Data
@Entity
@Table(name = "CAT_EMAIL_ERROR")
public class CatEmailError implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4347993307183658526L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "N_ID_CAT_EMAIL_ERROR")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_EDITION")
	private Edition edition;

	@Column(name = "S_DESCRIPCION")
	private String descripcion;

	@Column(name = "S_SUGGESTION_ES")
	private String suggestionEs;

	@Column(name = "S_SUGGESTION_EN")
	private String suggestionEn;

}
