package mx.fmre.rttycontest.api.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2547782797305462017L;
	private String email;
	private String password;
	private String token;
}
