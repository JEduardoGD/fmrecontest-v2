package mx.fmre.rttycontest.api.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class QsoDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 4142083484195909973L;
	
	private Long id;
	private Integer conteoId;
	private Integer frequency;
	private String callsignE;
	private String callsignR;
	private String dateTime;
	private String exchangeE;
	private String exchangeR;
	private String rstE;
	private String rstR;
	private Long dxccEntityId;
	private String dxccEntity;
	private Integer points;
	private boolean isMultiply;
	private String qsoBand;
	private Integer qsoBandId;
	
}
