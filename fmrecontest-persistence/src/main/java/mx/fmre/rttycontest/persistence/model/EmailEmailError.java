package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "REL_EMAIL_EMAIL_ERROR")
public class EmailEmailError implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -2111838561167843201L;
	
	@EmbeddedId
	private EmailEmailErrorPk pk;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="N_ID_EMAIL", insertable = false, updatable = false)
	private Email email;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="N_ID_CAT_EMAIL_ERROR", insertable = false, updatable = false)
	private CatEmailError emailError;
	
	@Column(name = "D_FECHA_REGISTRO")
	private Date fechaRegistro;
}

