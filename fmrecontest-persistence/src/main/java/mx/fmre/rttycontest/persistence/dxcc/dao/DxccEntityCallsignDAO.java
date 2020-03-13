package mx.fmre.rttycontest.persistence.dxcc.dao;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import mx.fmre.rttycontest.persistence.model.DxccEntity;

@AllArgsConstructor
@Data
public class DxccEntityCallsignDAO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6877842963186952037L;
	private DxccEntity dxccEntity;
	private String callsign;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DxccEntityCallsignDAO other = (DxccEntityCallsignDAO) obj;
		if (callsign == null) {
			if (other.callsign != null)
				return false;
		} else if (!callsign.equals(other.callsign))
			return false;
		if (dxccEntity == null) {
			if (other.dxccEntity != null)
				return false;
		} else if (!dxccEntity.equals(other.dxccEntity))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((callsign == null) ? 0 : callsign.hashCode());
		result = prime * result + ((dxccEntity == null) ? 0 : dxccEntity.hashCode());
		return result;
	}

}
