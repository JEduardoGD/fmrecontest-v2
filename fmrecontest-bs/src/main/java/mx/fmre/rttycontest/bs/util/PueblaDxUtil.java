package mx.fmre.rttycontest.bs.util;

import mx.fmre.rttycontest.bs.dxcc.dao.XmlObjectPueblaDX;
import mx.fmre.rttycontest.bs.dxcc.dao.XmlObjectPueblaDX.Call;
import mx.fmre.rttycontest.persistence.model.DxccEntity;

public abstract class PueblaDxUtil {
	public static DxccEntity parse(XmlObjectPueblaDX xmlObjectPueblaDX) {
		Call call = xmlObjectPueblaDX.getCalls().get(0);
		DxccEntity dxccEntity = new DxccEntity();
//		dxccEntity.setCall(call.getCallsign());
		dxccEntity.setId(null);
//		dxccEntity.setState(call.getEntidad());
		dxccEntity.setItu(call.getItu());
		dxccEntity.setCq(call.getCq());
		dxccEntity.setEntity(call.getEntidad());
		dxccEntity.setCont(call.getContinente());
		return dxccEntity;
	}
}
