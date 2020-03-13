package mx.fmre.rttycontest.bs.dxcc.dao;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class XmlObjectPueblaDX {

	@XmlElement(name = "Hosting")
	private Hosting hosting;

	@XmlElement(name = "CALL")
	private List<Call> calls;
	
	@XmlRootElement(name = "Hosting")
	@XmlAccessorType(XmlAccessType.FIELD)
	@Data
	static class Hosting {
		@XmlElement(name = "Proveedor")
		private String proveedor;

		@XmlElement(name = "Uso")
		private String uso;

		@XmlElement(name = "Programador")
		private String programador;

		@XmlElement(name = "versión")
		private String version;

	}
	
	@XmlRootElement(name = "CALL")
	@XmlAccessorType(XmlAccessType.FIELD)
	@Data
	public static class Call {
		@XmlElement(name = "Callsign")
		private String callsign;

		@XmlElement(name = "Prefijo")
		private String prefijo;

		@XmlElement(name = "Entidad")
		private String entidad;

		@XmlElement(name = "Continente")
		private String continente;

		@XmlElement(name = "ITU")
		private Integer itu;

		@XmlElement(name = "CQ")
		private Integer cq;

		@XmlElement(name = "UTC")
		private Integer utc;

		@XmlElement(name = "LAT")
		private String latitud;

		@XmlElement(name = "LONG")
		private String longitud;

		@XmlElement(name = "ITULoc")
		private String ituloc;

		@XmlElement(name = "Otros")
		private String otros;

		@XmlElement(name = "DXCC")
		private Integer dxcc;

		@XmlElement(name = "BD")
		private String bd;
	}
}