package mx.fmre.rttycontest.bs.dxcc.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

import lombok.Data;

@XmlRootElement(name = "DXCC")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class DxccentityModelDAO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4776433034919911152L;
	private Integer dxcc;
	private String cc;
	private String ccc;
	private String name;
	private String continent;
	private Integer ituzone;
	private Integer cqzone;
	private Integer timezone;
	private BigDecimal lat;
	private BigDecimal lon;
	@XmlElement(name = "Session")
	private QrzSessionDAO session;
}
