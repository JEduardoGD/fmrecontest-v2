package mx.fmre.rttycontest.recibir.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9217541072920816115L;
	private String suggestionEs;
	private String suggestionEn;
}
