package mx.fmre.rttycontest.persistence.dxcc.dao;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.DxccEntity;

@AllArgsConstructor
@Data
public class DxccEntityCallsignDAO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6877842963186952037L;
	private DxccEntity dxccEntity;
	private ContestQso qso;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DxccEntityCallsignDAO other = (DxccEntityCallsignDAO) obj;
		if (dxccEntity == null) {
			if (other.dxccEntity != null)
				return false;
		} else if (!dxccEntity.equals(other.dxccEntity))
			return false;
		if (qso == null) {
			if (other.qso != null)
				return false;
		} else if (!qso.equals(other.qso))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dxccEntity == null) ? 0 : dxccEntity.hashCode());
		result = prime * result + ((qso == null) ? 0 : qso.hashCode());
		return result;
	}

}
