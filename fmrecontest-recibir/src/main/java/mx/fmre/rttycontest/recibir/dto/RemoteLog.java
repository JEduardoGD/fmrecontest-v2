package mx.fmre.rttycontest.recibir.dto;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.fmre.rttycontest.persistence.model.ContestLog;

@Data
@NoArgsConstructor
public class RemoteLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private long idEmail;
	private String callsign;// perteneciente al id del objeto
	private BigInteger versionNumber;
	private String categoryAssisted;
	private String categoryBand;
	private String categoryMode;
	private String categoryOperator;
	private String categoryPower;
	private String categoryStation;
	private String categoryTime;
	private String categoryTransmitter;
	private String categoryOverlay;
	private int claimedScore;
	private String club;
	private String contest;
	private String createdBy;
	private String emailAddress;
	private String location;
	private String name;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String operators;
	private String offline;
	private String soapbox;
	private String anio;// perteneciente al id del objeto

	public RemoteLog(ContestLog contestLog) {
		this.id = contestLog.getId();
		this.idEmail = contestLog.getEmail() != null ? contestLog.getEmail().getId() : null;
		this.callsign = null;// no se setea para que sea seteado mas adelante en la llave del objeto
		this.versionNumber = null;
		this.categoryAssisted = null;
		this.categoryBand = contestLog.getCategoryBand();
		this.categoryMode = contestLog.getCategoryMode();
		this.categoryOperator = contestLog.getCategoryOperator();
		this.categoryPower = contestLog.getCategoryPower();
		this.categoryStation = null;
		this.categoryTime = null;
		this.categoryTransmitter = null;
		this.categoryOverlay = null;
		this.claimedScore = contestLog.getClaimedScore();
		this.club = contestLog.getClub();
		this.contest = contestLog.getContest();
		this.createdBy = contestLog.getCreateBy();
		this.emailAddress = contestLog.getEmailAddress();
		this.name = contestLog.getName();
		this.operators = contestLog.getOperators();
		this.offline = null;
		this.soapbox = contestLog.getSoapbox();
		this.anio = null;// no se setea para que sea seteado mas adelante en la llave del objeto
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RemoteLog other = (RemoteLog) obj;
		if (anio == null) {
			if (other.anio != null)
				return false;
		} else if (!anio.equalsIgnoreCase(other.anio))
			return false;
		if (callsign == null) {
			if (other.callsign != null)
				return false;
		} else if (!callsign.equalsIgnoreCase(other.callsign))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anio == null) ? 0 : anio.hashCode());
		result = prime * result + ((callsign == null) ? 0 : callsign.hashCode());
		return result;
	}

}
