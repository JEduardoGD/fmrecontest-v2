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
import lombok.ToString;

@Entity
@Data
@Table(name = "TBL_CONTEST_QSO")
@ToString
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

	@Column(name = "D_DXCC_NOT_FOUND")
	private Boolean dxccNotFound;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_CONTEST_LOG")
	private ContestLog contestLog;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "D_ENTITY_CODE")
	private DxccEntity dxccEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_FREQUENCY_BAND")
	private CatFrequencyBand frequencyBand;

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "contestQso", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<RelQsoConteo> relQsoConteos;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContestQso other = (ContestQso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	
}







