package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="CAT_FREQUENCY_BAND")
public class CatFrequencyBand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 41874882352998016L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "N_ID_FREQUENCY_BAND")
	private Integer id;
	
	@Column(name = "S_BAND")
	private String band;
	
	@Column(name = "D_START_FREQ")
	private BigDecimal startFrequency;
	
	@Column(name = "D_END_FREQ")
	private BigDecimal endFrequency;

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "frequencyBand", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<ContestQso> contestqsos;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CatFrequencyBand other = (CatFrequencyBand) obj;
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
