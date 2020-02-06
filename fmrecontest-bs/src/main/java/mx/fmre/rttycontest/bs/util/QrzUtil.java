package mx.fmre.rttycontest.bs.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import mx.fmre.rttycontest.bs.dxcc.dao.CallsignDAO;
import mx.fmre.rttycontest.bs.dxcc.dao.QRZDatabaseDAO;
import mx.fmre.rttycontest.bs.dxcc.dao.QrzSessionDAO;
import mx.fmre.rttycontest.bs.dxcc.util.MyNamespaceFilter;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.DxccSession;

public class QrzUtil {

	private static final String QRZ_URL = "http://xmldata.qrz.com/xml/1.31";

	public static QrzSessionDAO getNewQrzSession(String username, String password) throws FmreContestException {
		QRZDatabaseDAO qrzdatabase = null;

		String url = String.format(QRZ_URL + "/?username=%s;password=%s", username, password);

		qrzdatabase = callToQrz(url);
		
		if(     qrzdatabase.getSession() != null &&
				qrzdatabase.getSession().getKey() != null &&
				qrzdatabase.getSession().getError() == null
				) {
			return qrzdatabase.getSession();
		}
		throw new FmreContestException(qrzdatabase.getSession().getError());
	}

	public static QRZDatabaseDAO callToQrz(String url) throws FmreContestException {
		QRZDatabaseDAO qrzdatabase;
		byte[] targetArray;
		try {
			targetArray = HttpUtil.httpCall(url);
		} catch (IOException e) {
			throw new FmreContestException(e.getLocalizedMessage());
		}

		try (InputStream is = FileUtil.byteArrayToInputStream(targetArray)) {
			qrzdatabase = parseQrz(new InputSource(is));
		} catch (JAXBException | SAXException | IOException e) {
			throw new FmreContestException(e.getLocalizedMessage());
		}
		return qrzdatabase;
	}

	private static QRZDatabaseDAO parseQrz(InputSource is) throws JAXBException, SAXException {
		JAXBContext jc = JAXBContext.newInstance(QRZDatabaseDAO.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();

		XMLReader reader = XMLReaderFactory.createXMLReader();
		MyNamespaceFilter inFilter = new MyNamespaceFilter(null, true);
		inFilter.setParent(reader);
		SAXSource source = new SAXSource(inFilter, is);

		QRZDatabaseDAO qrzDatabase = (QRZDatabaseDAO) unmarshaller.unmarshal(source);
		return qrzDatabase;
	}
	
	public static DxccEntity parse(CallsignDAO callsignDAO) {
		DxccEntity dxccEntity = new DxccEntity();
		dxccEntity.setId(callsignDAO.getDxcc());
		dxccEntity.setEntity(callsignDAO.getCountry());
		dxccEntity.setCont(null);
		dxccEntity.setItu(callsignDAO.getItuzone());
		dxccEntity.setCq(callsignDAO.getCqzone());
		return dxccEntity;
	}
	
	public static DxccSession parse(QrzSessionDAO qrzSessionDAO) {
		DxccSession dxccSession = new DxccSession();
		dxccSession.setKey(qrzSessionDAO.getKey());
		dxccSession.setCount(qrzSessionDAO.getCount());
		dxccSession.setSubExp(qrzSessionDAO.getSubExp());
		dxccSession.setGmTime(qrzSessionDAO.getGmTime());
		dxccSession.setRemark(qrzSessionDAO.getRemark());
		dxccSession.setError(qrzSessionDAO.getError());
		return dxccSession;
	}

}
