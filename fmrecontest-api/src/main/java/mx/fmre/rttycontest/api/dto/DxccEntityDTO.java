package mx.fmre.rttycontest.api.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DxccEntityDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -4130413421550332675L;
	
	private Long id;
	private String entity;
	private String cont;
	private Integer itu;
	private Integer cq;
	private String origen;
}
