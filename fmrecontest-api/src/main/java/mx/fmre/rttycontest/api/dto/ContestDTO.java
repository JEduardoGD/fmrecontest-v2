package mx.fmre.rttycontest.api.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContestDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -5595731719033510921L;
	
	private Integer id;
	private String description;
	
}
