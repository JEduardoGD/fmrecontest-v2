package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "TBL_DXCC_ENTITY")
public class DxccEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5379087720321735976L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "N_ID_CONTEST_QSO")
	private Long id;
	
	@Column(name = "D_ENTITY_CODE")
	private Integer entityCode;

	@Column(name = "S_ENTITY")
	private String entity;

	@Column(name = "S_CONT")
	private String cont;

	@Column(name = "D_ITU")
	private Integer itu;

	@Column(name = "D_CQ")
	private Integer cq;
	
	@OneToOne(mappedBy = "dxccEntity", fetch = FetchType.LAZY)
	private ContestQso contestQso;
}
