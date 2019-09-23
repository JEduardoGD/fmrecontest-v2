package mx.fmre.rttycontest.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TBL_CONTEST")
public class Contest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "N_ID_CONTEST")
	private Integer id;

	@Column(name = "S_DESCRIPTION")
	private String description;

	@OneToMany(mappedBy = "contest")
    private List<Edition> editions = new ArrayList<>();

	@OneToOne(mappedBy = "contest", fetch = FetchType.EAGER)
	private EmailAccount emailAccount;
}
