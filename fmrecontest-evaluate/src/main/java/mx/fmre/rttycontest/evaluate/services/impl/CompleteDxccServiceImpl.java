package mx.fmre.rttycontest.evaluate.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

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
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;

@Service
public class CompleteDxccServiceImpl implements ICompleteDxccService {

	@Autowired private IEditionRepository    editionRepository;
	@Autowired private IEmailRepository      emailRepository;
	@Autowired private IContestLogRepository contestLogRepository;
	@Autowired private IContestQsoRepository contestQsoRepository;
	@Autowired private IDxccEntityRepository dxccEntityRepository;
	@Autowired private ApplicationContext    appContext;

	@Value("${messages.perminute}")
	private Integer messagesPerminute;

	

	@Override
	public void complete() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			Map<String, DxccEntity> map = this.fillDxccMap(edition);
			List<Email> emails = emailRepository.getAllWithLogfileByEdition(edition);
			if (emails.size() > messagesPerminute)
				emails = emails.subList(0, messagesPerminute);

			for (Email email : emails) {
				ContestLog contestLog = contestLogRepository.findByEmail(email);
				List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
				List<ContestQso> newQsos = contestQsos.stream().map(qso -> {
					DxccEntity dxccEntity = null;
					try {
						dxccEntity = getDxccOf(map, qso.getCallsignr(), edition);
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
		if(dxccEntity != null)
			return dxccEntity;
		
		// 2nd attempt, from de db, others saved
		List<DxccEntity> dxccEntities = dxccEntityRepository.getByCallsignOnEdition(callsign, edition);
		if(!dxccEntities.isEmpty()) {
			dxccEntity = dxccEntities.get(0);
			map.put(callsign, dxccEntity);
			return dxccEntity;
		}
		
		// 3rd attempt, from QRZ
		IDxccService dxccServiceQrz = appContext.getBean("dxccServiceQrzImpl", IDxccService.class);
		CallsignDAO res = dxccServiceQrz.getDxccFromCallsign(callsign);
		if (res != null) {
			Long dxccEntityNumber = res.getDxcc();
			dxccEntity = dxccEntityRepository.findById(dxccEntityNumber).orElse(null);
			if (dxccEntity != null) {
				map.put(callsign, dxccEntity);
				return dxccEntity;
			}
			if (dxccEntity == null && res != null) {
				dxccEntity = QrzUtil.parse(res);
				dxccEntity = dxccEntityRepository.save(dxccEntity);
				map.put(callsign, dxccEntity);
				return dxccEntity;
			}
		}
		
		// NOT FOUND
		return null;
	}
}
