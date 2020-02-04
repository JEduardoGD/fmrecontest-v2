package mx.fmre.rttycontest.bs.dxcc.service.impl;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dxcc.dao.CallsignDAO;
import mx.fmre.rttycontest.bs.dxcc.dao.QRZDatabaseDAO;
import mx.fmre.rttycontest.bs.dxcc.dao.QrzSessionDAO;
import mx.fmre.rttycontest.bs.dxcc.service.IDxccService;
import mx.fmre.rttycontest.bs.util.QrzUtil;
import mx.fmre.rttycontest.exception.FmreContestException;

@Slf4j
public class DxccServiceQrzImpl implements IDxccService {

	private static final String QRZ_URL = "http://xmldata.qrz.com/xml/1.31";

	@Override
	public CallsignDAO getDxccFromCallsign(String callsign) throws FmreContestException {
		log.info("Info from QRZData para " + callsign);
		QRZDatabaseDAO qrzdatabase = null;

		QrzSessionDAO session = QrzUtil.getNewQrzSession();

		if (session == null)
			return null;

		String url = String.format(QRZ_URL + "?s=%s;callsign=%s", session.getKey(), callsign);

		qrzdatabase = QrzUtil.callToQrz(url);

		if (qrzdatabase == null)
			return null;

		if (qrzdatabase.getSession() != null && qrzdatabase.getSession().getError() != null) {
			log.warn("Error de QRZ: " + qrzdatabase.getSession().getError());
			return null;
		}

		if (qrzdatabase != null && qrzdatabase.getCallsign() == null) {
			log.error("qrzdatabase es nulo");
			return null;
		}

		CallsignDAO callsignQuery = qrzdatabase.getCallsign();

		if (!callsignQuery.getCall().equalsIgnoreCase(callsign)) {
			return null;
		}

		return qrzdatabase.getCallsign();
	}
}
