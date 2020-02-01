package mx.fmre.rttycontest.bs.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AttachedFileDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6444311537786856268L;
	private int id;
	private String contentType;
	private String filename;
	private byte[] byteArray;
	private String contenyType;
	private int lenght;
	private String hash;
	private String path;
}
