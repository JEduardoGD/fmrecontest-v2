package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

@Data
@Entity
@Table(name = "TBL_EDITION")
public class Edition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3786395119566979968L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "N_ID_EDITION")
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "N_ID_CONTEST")
	private Contest contest;

	@Column(name = "S_DESCRIPTION")
	private String description;

	@Column(name = "N_YEAR")
	private int year;

	@Column(name = "T_START_UTC")
	private Date start;

	@Column(name = "T_END_UTC")
	private Date end;

	@Column(name = "N_ID_EMAIL_START")
	private Integer emailStart;

	@Column(name = "N_ID_EMAIL_END")
	private Integer emailEnd;

	@Column(name = "N_ACTIVE")
	private Boolean active;

	@OneToMany(mappedBy = "edition")
    private List<Email> emails = new ArrayList<>();
}
