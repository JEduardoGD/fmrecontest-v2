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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "TBL_CONTEST_QSO")
public class ContestQso implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1641391992511917272L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "N_ID_CONTEST_QSO")
	private Long id;

	@Column(name = "S_CALLSIGN_EMISOR")
	private String callsigne;

	@Column(name = "S_CALLSIGN_RECEPTOR")
	private String callsignr;

	@Column(name = "D_DATETIME")
	private Date datetime;

	@Column(name = "S_EXCHANGE_EMMITED")
	private String exchangee;

	@Column(name = "S_EXCHANGE_RECEIVED")
	private String exchanger;

	@Column(name = "N_FREQUENCY")
	private int frequency;

	@Column(name = "S_MODE")
	private String mode;

	@Column(name = "S_RST_EMMITED")
	private String rste;

	@Column(name = "S_RST_RECEIVED")
	private String rstr;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_CONTEST_LOG")
	private ContestLog contestLog;
	
	@OneToOne
	@JoinColumn(name = "N_ID_DXCC_ENTITY")
	private DxccEntity dxccEntity;
}







