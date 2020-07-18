package mx.fmre.rttycontest.bs.dxcc.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "QRZDatabase")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class QRZDatabaseDAO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -130057701480613577L;

	@XmlAttribute
	private BigDecimal version;

	@XmlElement(name = "Callsign")
	private QrzCallsignDAO callsign;

	@XmlElement(name = "DXCC")
	private DxccentityModelDAO dxcc;

	@XmlElement(name = "Session")
	private QrzSessionDAO session;
}