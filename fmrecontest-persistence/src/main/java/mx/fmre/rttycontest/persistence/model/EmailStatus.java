package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "CAT_EMAIL_STATUS")
public class EmailStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3562366182904586246L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "N_ID_EMAIL_STATUS")
	private Integer id;

	@Column(name = "S_STATUS")
	private String status;
	
	@Column(name = "S_DESCRIPTION")
	private String description;

	@OneToMany(mappedBy = "emailStatus", fetch = FetchType.LAZY)
    private List<Email> emails = new ArrayList<>();
}
