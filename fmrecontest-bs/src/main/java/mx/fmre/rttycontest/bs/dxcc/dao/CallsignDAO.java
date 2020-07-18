package mx.fmre.rttycontest.bs.dxcc.dao;

import java.io.Serializable;

import lombok.Data;

@Data
public class CallsignDAO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7776296670619589174L;
	private String call;
	private Long dxcc;
	private String state;
	private Integer ituzone;
	private Integer cqzone;
	private String country;
	private String cont;
}
