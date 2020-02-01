package mx.fmre.rttycontest.api.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5618947251246103709L;

	private String email;
	private String password;
}
