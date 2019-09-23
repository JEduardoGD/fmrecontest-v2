package mx.fmre.rttycontest.web.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EditionReturnDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8017523623463067491L;
	private Integer id;
	private String contestDescription;
	private String description;
	private Integer year;
	private LocalDateTime start;
	private LocalDateTime end;
}
