package mx.fmre.rttycontest.evaluate.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dxcc.dao.CallsignDAO;
import mx.fmre.rttycontest.bs.dxcc.service.IDxccService;
import mx.fmre.rttycontest.bs.util.QrzUtil;
import mx.fmre.rttycontest.evaluate.services.ICompleteDxccService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.dxcc.dao.DxccEntityCallsignDAO;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.LastEmail;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.persistence.repository.ILastEmailRepository;

@Service
@Slf4j
public class CompleteDxccServiceImpl implements ICompleteDxccService {

	@Autowired private IEditionRepository    editionRepository;
	@Autowired private IEmailRepository      emailRepository;
	@Autowired private IContestLogRepository contestLogRepository;
	@Autowired private IContestQsoRepository contestQsoRepository;
	@Autowired private IDxccEntityRepository dxccEntityRepository;
	@Autowired private ApplicationContext    appContext;
	@Autowired private ILastEmailRepository lastEmailRepository;

	@Value("${messages.perminute}")
	private Integer messagesPerminute;
	
	private static final String QRZ_ORIGEN = "QRZ.com";
	private static final String PUEBLA_DX_ORIGEN = "Puebla DX";
	
	@Override
	public void completeQsos() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			List<LastEmail> lastEmails = lastEmailRepository.findByEditionId(edition.getId());
			List<Integer> lastEmailIdsList = lastEmails
					.stream()
					.map(LastEmail::getEmailId)
					.collect(Collectors.toList());
			
			Map<String, DxccEntity> map = this.fillDxccMap(edition);
			
			List<Email> emails = emailRepository.getAllWithLogfileByEdition(edition);
			List<Email> filtered = emails
					.stream()
					.filter(e -> lastEmailIdsList.contains(e.getId()))
					.collect(Collectors.toList());
			if (filtered.size() > messagesPerminute)
				filtered = filtered.subList(0, messagesPerminute);

			for (Email email : filtered) {
				ContestLog contestLog = contestLogRepository.findByEmail(email);
				List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
				List<ContestQso> newQsos = contestQsos.stream().map(qso -> {
					DxccEntity dxccEntity = null;
					try {
						dxccEntity = getDxccOf(map, qso.getCallsignr(), edition);
						if (dxccEntity == null)
							qso.setDxccNotFound(true);
						else
							qso.setDxccNotFound(false);
						qso.setDxccEntity(dxccEntity);
					} catch (FmreContestException e) {
						e.printStackTrace();
					}
					return qso;
				}).collect(Collectors.toList());
				contestQsoRepository.saveAll(newQsos);
			}
		}
	}
	
	private Map<String, DxccEntity> fillDxccMap(Edition edition) {
		Map<String, DxccEntity> map = new HashMap<>();
		List<DxccEntityCallsignDAO> listObjects = dxccEntityRepository.getAllByEdition(edition);
		listObjects.stream().forEach(o -> map.put(o.getQso().getCallsignr(), o.getDxccEntity()));
		return map;
	}

	private DxccEntity getDxccOf(Map<String, DxccEntity> map, String callsign, Edition edition) throws FmreContestException {
		DxccEntity dxccEntity;
		
		// 1st atempt, from de map.
		dxccEntity = map.get(callsign);
		if(dxccEntity != null) {
			log.info("{} from map", callsign);
			return dxccEntity;
		}
		
		// 2nd attempt, from de db, others saved
		List<DxccEntity> dxccEntities = dxccEntityRepository.getByCallsignOnEdition(callsign, edition);
		if(!dxccEntities.isEmpty()) {
			log.info("{} from db, others saved", callsign);
			dxccEntity = dxccEntities.get(0);
			map.put(callsign, dxccEntity);
			return dxccEntity;
		}
		
		// 3rd attempt, from QRZ
		IDxccService dxccServiceQrz = appContext.getBean("qrzDxccServiceQrzImpl", IDxccService.class);
		CallsignDAO resQrz = dxccServiceQrz.getDxccFromCallsign(callsign);
		if (resQrz != null) {
			Long dxccEntityNumber = resQrz.getDxcc();
			dxccEntity = dxccEntityRepository.findById(dxccEntityNumber).orElse(null);
			if (dxccEntity != null) {
				log.info("{} from qrz", callsign);
				map.put(callsign, dxccEntity);
				return dxccEntity;
			}
			if (dxccEntity == null && resQrz != null) {
				dxccEntity = QrzUtil.parse(resQrz);
				dxccEntity.setOrigen(QRZ_ORIGEN);
				dxccEntity = dxccEntityRepository.save(dxccEntity);
				map.put(callsign, dxccEntity);
				return dxccEntity;
			}
		}
		
		// 3rd attempt, from Puebla DX
		IDxccService dxccServicePueblaDx = appContext.getBean("dxccServicePueblaDx", IDxccService.class);
		CallsignDAO resPueblaDx = dxccServicePueblaDx.getDxccFromCallsign(callsign);
		if (resPueblaDx != null) {
			Long dxccEntityNumber = resPueblaDx.getDxcc();
			dxccEntity = dxccEntityRepository.findById(dxccEntityNumber).orElse(null);
			if (dxccEntity != null) {
				map.put(callsign, dxccEntity);
				return dxccEntity;
			}
			if (dxccEntity == null && resPueblaDx != null) {
				log.info("{} from Puebla DX", callsign);
				dxccEntity = QrzUtil.parse(resPueblaDx);
				dxccEntity.setOrigen(PUEBLA_DX_ORIGEN);
				dxccEntity = dxccEntityRepository.save(dxccEntity);
				map.put(callsign, dxccEntity);
				return dxccEntity;
			}
		}
		
		// NOT FOUND
		return null;
	}
}
