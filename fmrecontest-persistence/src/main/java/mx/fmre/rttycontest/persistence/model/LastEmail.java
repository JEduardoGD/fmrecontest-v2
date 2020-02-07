package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "V_LAST_EMAIL")
public class LastEmail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4878441081230535587L;

	@Id
	@Column(name = "EMAIL_ID")
	private Integer emailId;
	
	@Column(name = "EDITION_ID")
    private Integer editionId;
	
	@Column(name = "N_ID_CONTEST_LOG")
    private Integer contestLogId;
	
    @Column(name = "EMAIL_SUBJECT")
    private String emailSubject;
    
    @Column(name = "EMAIL_STATUS")
    private String emailStatus;
}
