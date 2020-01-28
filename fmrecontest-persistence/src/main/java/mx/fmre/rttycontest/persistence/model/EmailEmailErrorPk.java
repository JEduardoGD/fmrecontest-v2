package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class EmailEmailErrorPk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7967327226924248631L;

	@Column(name = "N_ID_EMAIL", insertable=false, updatable=false)
	private Integer emailId;

	@Column(name = "N_ID_CAT_EMAIL_ERROR", insertable=false, updatable=false)
	private Integer catErrorid;
}
