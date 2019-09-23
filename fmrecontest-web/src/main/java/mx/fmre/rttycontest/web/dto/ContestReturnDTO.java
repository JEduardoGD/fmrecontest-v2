package mx.fmre.rttycontest.web.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContestReturnDTO implements Serializable {
	private static final long serialVersionUID = -3455552406315425041L;

	private Integer id;
	private String descripcion;
}
