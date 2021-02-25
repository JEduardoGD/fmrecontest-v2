package mx.fmre.rttycontest.bs.dxcc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dxcc.dao.CallsignDAO;
import mx.fmre.rttycontest.bs.dxcc.dao.DxccentityModelDAO;
import mx.fmre.rttycontest.bs.dxcc.dao.QRZDatabaseDAO;
import mx.fmre.rttycontest.bs.dxcc.dao.QrzSessionDAO;
import mx.fmre.rttycontest.bs.dxcc.service.IDxccService;
import mx.fmre.rttycontest.bs.util.QrzUtil;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.DxccSession;
import mx.fmre.rttycontest.persistence.repository.IDxccSessionRepository;

@Slf4j
@Service("qrzDxccServiceQrzImpl")
public class QrzDxccServiceQrzImpl implements IDxccService {
	
	@Autowired private IDxccSessionRepository dxccSessionRepository;

	@Value("${qrz.username}")
	private String qrzUsername;
	
	@Value("${qrz.password}")
	private String qrzPassword;
	private static final String QRZ_URL = "https://xmldata.qrz.com/xml/1.34/";

	@Override
	public CallsignDAO getDxccFromCallsign(String callsign) throws FmreContestException {
		log.debug("Info from QRZData para {}", callsign);
		QRZDatabaseDAO qrzdatabase = null;
		
		DxccSession session = getActiveSession();

		String url = String.format(QRZ_URL + "?s=%s;dxcc=%s", session.getKey(), callsign);

		qrzdatabase = QrzUtil.callToQrz(url);

		if (qrzdatabase == null) {
			return null;
		}
		
		if (
				qrzdatabase.getSession() != null &&
				qrzdatabase.getSession().getError() != null &&
				(
						"Invalid session key".equalsIgnoreCase(qrzdatabase.getSession().getError()) ||
						"Session Timeout".equalsIgnoreCase(qrzdatabase.getSession().getError())
						)) {
			session = getNewSession(qrzUsername, qrzPassword);
			url = String.format(QRZ_URL + "?s=%s;callsign=%s", session.getKey(), callsign);
			qrzdatabase = QrzUtil.callToQrz(url);
			if (qrzdatabase == null) {
				return null;
			}
		}

        if (qrzdatabase.getSession() != null && qrzdatabase.getSession().getError() != null) {
            log.warn("Error de QRZ: {}", qrzdatabase.getSession().getError());
            return null;
        }

        if (qrzdatabase == null || qrzdatabase.getDxcc() == null || qrzdatabase.getDxcc().getDxcc() == null) {
            log.error("qrzdatabase es nulo");
            return null;
        }

		DxccentityModelDAO callsignQuery = qrzdatabase.getDxcc();
		
		CallsignDAO callsignDAO =  QrzUtil.parse(callsignQuery);
		callsignDAO.setCall(callsign);

		return callsignDAO;
	}
	
	private DxccSession getActiveSession() throws FmreContestException {
		DxccSession session;
		List<DxccSession> sessions = dxccSessionRepository.getActiveSessions();
		if(sessions != null && !sessions.isEmpty())
			session = sessions.get(sessions.size() - 1);
		else {
			session = this.getNewSession(qrzUsername, qrzPassword);
		}
		
		return session;
	}
	
	private DxccSession getNewSession(String qrzUsername, String qrzPassword) throws FmreContestException {
		DxccSession session;
		try {
			QrzSessionDAO qrzSessionDAO = QrzUtil.getNewQrzSession(qrzUsername, qrzPassword);
			session = QrzUtil.parse(qrzSessionDAO);
			session = dxccSessionRepository.save(session);
		} catch (FmreContestException e) {
			throw e;
		}
		return session;
	}
}
