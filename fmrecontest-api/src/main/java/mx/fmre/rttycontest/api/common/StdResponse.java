package mx.fmre.rttycontest.api.common;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StdResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4074547802754259216L;
	private String messageResponse;
	private int statusResponse;
	private Object data;

	public StdResponse() {
		this.messageResponse = "OK";
		this.statusResponse = 200;
	}

	public StdResponse(String messageResponse, int statusResponse, Object data) {
		this.messageResponse = "OK";
		this.statusResponse = 200;
	}

}
