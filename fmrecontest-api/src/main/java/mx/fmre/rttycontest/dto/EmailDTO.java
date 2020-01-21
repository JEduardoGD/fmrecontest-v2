package mx.fmre.rttycontest.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmailDTO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -159786066504626110L;
	private int id;
	private String subject;
	private String sentDate;
	private String recipientsFromName;
	private String recipientsFromAddress;
	private String emailStatus;
}
