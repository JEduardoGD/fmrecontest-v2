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

	@ManyToOne(fetch = FetchType.EAGER)
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
}
