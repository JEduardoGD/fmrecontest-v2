package mx.fmre.rttycontest.recibir.exception;

import org.springframework.web.client.ResourceAccessException;

public class ExternalConnectionException extends Exception {

	public ExternalConnectionException(ResourceAccessException e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5730013325438694095L;

}
