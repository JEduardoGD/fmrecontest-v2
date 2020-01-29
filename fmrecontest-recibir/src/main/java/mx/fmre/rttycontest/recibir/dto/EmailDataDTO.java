package mx.fmre.rttycontest.recibir.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import mx.fmre.rttycontest.persistence.model.CatEmailError;

@Data
public class EmailDataDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1128237835511103127L;
	
	private List<CatEmailError> errors;
	private String subject;
	private String describeErrors;
	private String recipientFrom;
	private String recipientTo;
}
