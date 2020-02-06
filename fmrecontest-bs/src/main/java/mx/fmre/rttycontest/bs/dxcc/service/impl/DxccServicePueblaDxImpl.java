package mx.fmre.rttycontest.bs.dxcc.service.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dxcc.dao.CallsignDAO;
import mx.fmre.rttycontest.bs.dxcc.dao.XmlObjectPueblaDX;
import mx.fmre.rttycontest.bs.dxcc.service.IDxccService;
import mx.fmre.rttycontest.bs.util.FileUtil;
import mx.fmre.rttycontest.bs.util.HttpUtil;
import mx.fmre.rttycontest.bs.util.PueblaDxUtil;
import mx.fmre.rttycontest.exception.FmreContestException;

@Slf4j
@Service("dxccServicePueblaDx")
public class DxccServicePueblaDxImpl implements IDxccService {

	private static final String PUEBLADX_URL = "http://logs.puebladx.org/DXCCinfo.php";

	@Override
	public CallsignDAO getDxccFromCallsign(String callsign) throws FmreContestException {
		XmlObjectPueblaDX xmlObjectPueblaDX = doRequest(callsign);
		if (xmlObjectPueblaDX == null || xmlObjectPueblaDX.getCalls() == null || xmlObjectPueblaDX.getCalls().isEmpty()) {
			return null;
		}
		
		if (xmlObjectPueblaDX == null || xmlObjectPueblaDX.getCalls().size() > 1) {
			throw new FmreContestException("more than 1 call eelement found on puebladx for " + callsign);
		}
		
		CallsignDAO callsignDAO = PueblaDxUtil.parse(xmlObjectPueblaDX);
		
		
		return callsignDAO;
	}

	public XmlObjectPueblaDX doRequest(String callsign) throws FmreContestException {
		XmlObjectPueblaDX xmlObjectPueblaDX = null;

		log.info("Info from PueblaDX para {}", callsign);
		String url = String.format(PUEBLADX_URL + "?CALL=%s", callsign);

		byte[] targetArray = null;
		try {
			targetArray = HttpUtil.httpCall(url);
		} catch (IOException e) {
			throw new FmreContestException(e.getLocalizedMessage());
		}
		InputStream inputStream = FileUtil.byteArrayToInputStream(targetArray);
		InputSource inputSource = new InputSource(inputStream);
		try {
			xmlObjectPueblaDX = parsePueblaDX(inputSource);
		} catch (JAXBException | SAXException e) {
			throw new FmreContestException(e.getLocalizedMessage());
		}

		if (xmlObjectPueblaDX == null)
			return null;

		return xmlObjectPueblaDX;
	}

	private static XmlObjectPueblaDX parsePueblaDX(InputSource is) throws JAXBException, SAXException {
		JAXBContext jaxbContext = JAXBContext.newInstance(XmlObjectPueblaDX.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		XmlObjectPueblaDX xmlObjectPueblaDX = (XmlObjectPueblaDX) jaxbUnmarshaller.unmarshal(is);
		return xmlObjectPueblaDX;
	}

}
