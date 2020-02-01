package mx.fmre.rttycontest.api.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AttachedFileDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7260922443447338948L;
	private Integer id;
	private String filename;
	private String contentType;
	private Integer lenght;
	private String path;
}
