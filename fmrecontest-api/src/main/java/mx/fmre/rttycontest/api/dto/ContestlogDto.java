package mx.fmre.rttycontest.api.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContestlogDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -748884628555425922L;
	
	private Long id;
	private Integer emailId; 
}
