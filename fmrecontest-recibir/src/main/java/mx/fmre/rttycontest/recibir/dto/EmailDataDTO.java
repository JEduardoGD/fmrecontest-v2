package mx.fmre.rttycontest.recibir.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class EmailDataDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1128237835511103127L;
	
	private List<ErrorDTO> errors;
	private List<OthersLogs> othersLogs;
	private String subject;
	private String emailSubject;
	private String describeErrors;
	private String fromName;
	private String fromAddress;
	private String toName;
	private String toAddress;
	private String template;
	private Date dateOfSend;
	private String callsign;
	private int noQsos;
	private String debugData;
	private String bcc;
}
