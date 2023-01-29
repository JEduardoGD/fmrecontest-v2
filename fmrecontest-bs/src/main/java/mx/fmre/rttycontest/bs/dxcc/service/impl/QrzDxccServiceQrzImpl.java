package mx.fmre.rttycontest.bs.dxcc.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dxcc.dao.DxccentityModelDAO;
import mx.fmre.rttycontest.bs.dxcc.dao.QRZDatabaseDAO;
import mx.fmre.rttycontest.bs.dxcc.dao.QrzSessionDAO;
import mx.fmre.rttycontest.bs.dxcc.service.IDxccService;
import mx.fmre.rttycontest.bs.util.QrzUtil;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
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
	public DxccEntity getDxccFromCallsign(String callsign) throws FmreContestException {
		log.debug("Info from QRZData para {}", callsign);
		QRZDatabaseDAO qrzdatabase = null;
		
		DxccSession session = getActiveSession();

        String url = null;
        try {
            url = makeQrzUrl(session, callsign);
        } catch (UnsupportedEncodingException e) {
            throw new FmreContestException(e);
        }
        if (url == null) {
            return null;
        }

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
            try {
                url = makeQrzUrl(session, callsign);
            } catch (UnsupportedEncodingException e) {
                log.error(e.getLocalizedMessage());
            }
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

		DxccentityModelDAO dxccentityModelDAO = qrzdatabase.getDxcc();

		return QrzUtil.parse(dxccentityModelDAO);
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
    
    private String makeQrzUrl(DxccSession session, String callsign) throws UnsupportedEncodingException {
        return String.format(QRZ_URL + "?s=%s;dxcc=%s", session.getKey(), URLEncoder.encode(callsign.toUpperCase(), "UTF-8"));
    }
}
