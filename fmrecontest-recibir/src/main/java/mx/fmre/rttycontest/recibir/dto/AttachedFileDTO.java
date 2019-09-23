package mx.fmre.rttycontest.recibir.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AttachedFileDTO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -3402812034706564135L;
	private String filename;
	private byte[] byteArray;
	private String contenyType;
	private int lenght;
	private String hash;
	private String path;
}
