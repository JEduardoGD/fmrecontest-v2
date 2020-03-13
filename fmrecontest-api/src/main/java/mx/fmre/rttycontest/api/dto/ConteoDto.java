package mx.fmre.rttycontest.api.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ConteoDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6070248344644448215L;
	private Integer id;
	private String description;
	private Date datetime;
	private Integer editionId;
	private String edition;
	private Integer contestId;
	private String contest;
	private boolean noComplete;
}
