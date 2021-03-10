package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TBL_CONTEST_LOG")
public class ContestLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2148036771250831124L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "N_ID_CONTEST_LOG")
	private Long id;

	@Column(name = "S_ADDRESS")
	private String address;

	@Column(name = "S_ADDRESS_CITY")
	private String addressCity;

	@Column(name = "S_ADDRESS_COUNTRY")
	private String addressCountry;

	@Column(name = "S_ADDRESS_STATE_PROVINCE")
	private String addressStateProvince;

//	@Column(name = "S_ADDRESS_CALLSIGN")
	@Column(name = "S_CALLSIGN")
	private String callsign;

//	@Column(name = "S_ADDRESS_CATEGORY_BAND")
	@Column(name = "S_CATEGORY_BAND")
	private String categoryBand;

//	@Column(name = "S_ADDRESS_CATEGORY_MODE")
	@Column(name = "S_CATEGORY_MODE")
	private String categoryMode;

	@Column(name = "S_CATEGORY_OPERATOR")
	private String categoryOperator;

	@Column(name = "S_CATEGORY_POWER")
	private String categoryPower;

	@Column(name = "D_CLAIMED_SCORE")
	private int claimedScore;

	@Column(name = "S_CLUB")
	private String club;

	@Column(name = "S_CONTEST")
	private String contest;

	@Column(name = "S_CREATED_BY")
	private String createBy;

	@Column(name = "S_EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name = "S_NAME")
	private String name;

	@Column(name = "S_OPERATORS")
	private String operators;

	@Column(name = "S_SOAPBOX")
	private String soapbox;

	@Column(name = "D_IS_CHECKLOG")
	private boolean checklog;

	@OneToOne
	@JoinColumn(name = "N_ID_EMAIL")
	private Email email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_DXCCENTITY")
	private DxccEntity dxccEntity;

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "contestLog", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<ContestQso> contestqsos;

	@Column(name = "D_DXCC_NOT_FOUND")
	private Boolean dxccNotFound;

	@OneToMany(mappedBy = "contest", fetch = FetchType.LAZY)
    private List<Edition> editions = new ArrayList<>();

	// bi-directional many-to-one association to Contestqso
	@OneToMany(mappedBy = "contestLog", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<RelConteoContestLog> relConteoContestLogs;
}
