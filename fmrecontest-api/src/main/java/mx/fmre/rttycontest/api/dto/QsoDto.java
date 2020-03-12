package mx.fmre.rttycontest.api.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class QsoDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 4142083484195909973L;
	
	private Long id;
	private int frequency;
	private String callsignE;
	private String callsignR;
	private Date dateTime;
	private String exchangeE;
	private String exchangeR;
	private String rstE;
	private String rstR;
	private String dxccNotFound;
	private String dxccEntity;
	private Integer points;
	private boolean isMultiply;
	private String qsoBandNotFound;
	private String qsoBand;
	
}
