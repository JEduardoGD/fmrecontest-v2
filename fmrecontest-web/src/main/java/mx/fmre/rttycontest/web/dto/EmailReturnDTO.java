package mx.fmre.rttycontest.web.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmailReturnDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2549900867684028300L;
	private Integer idEmail;
	private Integer emailCount;
	private Integer idEdition;
	private String from;
	private String receivedDate;
	private String sentDate;
	private String subject;
}
