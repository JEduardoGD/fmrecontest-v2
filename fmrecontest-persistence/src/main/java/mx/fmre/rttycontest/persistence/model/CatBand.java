package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
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
@Table(name = "CAT_BANDS")
public class CatBand implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 6827293241759217393L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "N_ID_BAND")
	private Integer id;
	
	@Column(name = "S_BAND")
	private String band;

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "band", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<CatFrequencyBand> catFrequencyBands;

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "band", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<ContestQso> contestqsos;

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "band", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<StaticFrequency> StaticFrequencies;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CatBand other = (CatBand) obj;
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
