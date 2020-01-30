package mx.fmre.rttycontest.recibir.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class OthersLogs implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2953660485131167468L;
	private String fromName;
	private Date dateOfSend;
	private int noQsos;
}
