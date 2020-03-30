package mx.fmre.rttycontest.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class FrequencyDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8412600244824101559L;
	
	private Integer id;
	private String band;
	private BigDecimal startFrequency;
	private BigDecimal endFrequency;
}
