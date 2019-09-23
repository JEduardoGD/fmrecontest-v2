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
@Table(name = "TBL_ATTACHED_FILE")
public class AttachedFile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 345181349943945869L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "N_ID_ATTACHED_FILE")
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "N_ID_EMAIL")
	private Email email;

	@Column(name = "S_FILENAME")
	private String filename;
	
	@Column(name = "S_CONTENT_TYPE")
	private String contentType;
	
	@Column(name = "N_LENGHT")
	private Integer lenght;
	
	@Column(name = "S_MD5_HASH")
	private String md5Hash;
	
	@Column(name = "S_PATH")
	private String path;
}
