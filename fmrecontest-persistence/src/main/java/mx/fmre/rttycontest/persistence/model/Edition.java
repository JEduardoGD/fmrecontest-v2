package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
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

	@ManyToOne(fetch = FetchType.LAZY)
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

	@Column(name = "S_TEMPLATE")
	private String template;

	@Column(name = "S_QSO_PARSER_IMPL")
	private String qsoParserImpl;

	@Column(name = "S_QSO_VALIDATION_IMPL")
	private String qsoValidationImpl;

	@Column(name = "N_TEST")
	private Boolean test;

	@Column(name = "S_EMAIL_TEST")
	private String emailTest;
	
	@OneToMany(mappedBy = "edition", fetch = FetchType.LAZY)
    private List<Email> emails = new ArrayList<>();

	@OneToMany(mappedBy = "edition", fetch = FetchType.LAZY)
    private List<CatEmailError> catErrors = new ArrayList<>();

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "edition", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<CatQsoError> qsoErrors;

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "edition", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<Conteo> conteos;
}
