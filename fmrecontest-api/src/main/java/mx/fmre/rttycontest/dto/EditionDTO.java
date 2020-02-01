package mx.fmre.rttycontest.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class EditionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8625492660651805653L;
	private Integer id;
	private String description;
	private String contest;
	private Integer year;
	private String startDate;
	private String endDate;
}
