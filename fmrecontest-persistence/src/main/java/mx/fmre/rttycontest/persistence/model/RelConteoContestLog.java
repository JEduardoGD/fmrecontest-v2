package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "REL_CONTEO_CONTEST_LOG")
public class RelConteoContestLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7424878170899743171L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "N_ID_REL_CONTEO_CONTEST_LOG")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_CONTEO")
	private Conteo conteo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_CONTEST_LOG")
	private ContestLog contestLog;
	
	@Column(name = "N_SUM_OF_POINTS")
	private int sumOfPoints;
	
	@Column(name = "N_MULTIPLIERS")
	private long multipliers;
	
	@Column(name = "N_TOTAL_POINTS")
	private long totalPoints;
	
	@Column(name = "IS_COMPLETE")
	private boolean complete;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelConteoContestLog other = (RelConteoContestLog) obj;
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
