package mx.fmre.rttycontest.api.common;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import lombok.Data;

@Data
public class BaseController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1043923955868442922L;
	private StdResponse responseServiceVo;
	@Autowired
	private Environment environment;

	public BaseController() {
		responseServiceVo = new StdResponse();
	}

	public void resetResponse() {
		responseServiceVo = new StdResponse();
	}

}