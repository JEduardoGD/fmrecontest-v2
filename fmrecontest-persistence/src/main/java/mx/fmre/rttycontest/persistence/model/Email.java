package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TBL_EMAIL")
public class Email implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 345181349943945869L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "N_ID_EMAIL")
	private Integer id;

	@Column(name = "N_EMAIL_COUNT")
	private Integer emailCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_EDITION")
	private Edition edition;

	@Column(name = "S_RECIPIENTS_FROM_NAME")
	private String recipientsFromName;

	@Column(name = "S_RECIPIENTS_FROM_ADDRESS")
	private String recipientsFromAddress;

	@Column(name = "D_RECEIVED_DATE")
	private Date receivedDate;

	@Column(name = "S_RECIPIENTS_TO")
	private String recipientsTo;

	@Column(name = "D_SENT_DATE")
	private Date sentDate;

	@Column(name = "S_SUBJECT")
	private String subject;

	@OneToMany(mappedBy = "email", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<AttachedFile> attachedFiles = new ArrayList<>();

	@Column(name = "VERIFIED_AT")
	private Date verifiedAt;

	@Column(name = "ANSWERED_AT")
	private Date answeredAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_EMAIL_STATUS")
	private EmailStatus emailStatus;
	
	@OneToOne(mappedBy = "email", fetch = FetchType.LAZY)
	private ContestLog contestLog;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "REL_EMAIL_EMAIL_ERROR", joinColumns = @JoinColumn(name = "N_ID_EMAIL"), inverseJoinColumns = @JoinColumn(name = "N_ID_CAT_EMAIL_ERROR"))
	private Set<CatEmailError> emailErrors;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
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
