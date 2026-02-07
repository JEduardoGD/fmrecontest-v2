package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalLogsId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1746642548240678263L;
    @Column(name = "callsign")
	private String callsign;
    @Column(name = "ano")
	private String ano;
}
