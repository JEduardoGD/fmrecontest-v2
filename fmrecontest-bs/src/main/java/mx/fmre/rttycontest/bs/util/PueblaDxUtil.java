package mx.fmre.rttycontest.bs.util;

import mx.fmre.rttycontest.bs.dxcc.dao.CallsignDAO;
import mx.fmre.rttycontest.bs.dxcc.dao.XmlObjectPueblaDX;
import mx.fmre.rttycontest.bs.dxcc.dao.XmlObjectPueblaDX.Call;

public abstract class PueblaDxUtil {
	public static CallsignDAO parse(XmlObjectPueblaDX xmlObjectPueblaDX) {
		Call call = xmlObjectPueblaDX.getCalls().get(0);
		CallsignDAO callsignDAO = new CallsignDAO();
		callsignDAO.setCall(call.getCallsign());
		callsignDAO.setDxcc(Long.valueOf(call.getDxcc()));
		callsignDAO.setState(call.getEntidad());
		callsignDAO.setItuzone(call.getItu());
		callsignDAO.setCqzone(call.getCq());
		callsignDAO.setCountry(call.getEntidad());
		callsignDAO.setCont(call.getContinente());
		return callsignDAO;
	}
}
