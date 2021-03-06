package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TBL_EMAIL_ACCOUNT")
public class EmailAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3554856035045760278L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "N_ID_EMAIL_ACCOUNT")
	private Integer id;

	@OneToOne
	@JoinColumn(name = "N_ID_CONTEST")
	private Contest contest;

	@Column(name = "S_IN_HOST")
	private String inHost;

	@Column(name = "S_OUT_HOST")
	private String outHost;

	@Column(name = "S_EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name = "S_USERNAME")
	private String username;

	@Column(name = "S_PASSWORD")
	private String password;

	@Column(name = "N_IN_PORT")
	private Integer inPort;
	
	@Column(name = "N_OUT_PORT")
	private Integer outPort;
	
	@Column(name = "SUCCESSFULLY_RESPONSE_MSG")
	private String successfullyMsg;
	
	@Column(name = "ERROR_RESPONSE_MSG")
	private String errorMsg;
	
	@Column(name = "S_SMTP_PROPERTIES")
	private String smtpProperties;
	
	@Column(name = "S_REPLY_TO_NAME")
	private String replyToName;
	
	@Column(name = "S_REPLY_TO_EMAIL")
	private String replyToEmail;
}
