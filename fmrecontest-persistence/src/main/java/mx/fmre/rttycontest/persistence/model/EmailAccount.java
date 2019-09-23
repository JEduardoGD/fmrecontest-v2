package mx.fmre.rttycontest.persistence.model;

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
public class EmailAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "N_ID_EMAIL_ACCOUNT")
	private Integer id;

	@OneToOne
	@JoinColumn(name = "N_ID_CONTEST")
	private Contest contest;

	@Column(name = "S_HOST")
	private String smtpServer;

	@Column(name = "S_EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name = "S_USERNAME")
	private String username;

	@Column(name = "S_PASSWORD")
	private String password;

	@Column(name = "N_PORT")
	private Integer port;
}
