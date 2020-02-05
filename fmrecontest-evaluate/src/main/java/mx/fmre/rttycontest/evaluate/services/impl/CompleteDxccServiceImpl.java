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

	@Autowired
	private IEditionRepository editionRepository;
	@Autowired
	private IEmailRepository emailRepository;
	@Autowired
	private IContestLogRepository contestLogRepository;
	@Autowired
	private IContestQsoRepository contestQsoRepository;
	@Autowired
	private IDxccEntityRepository dxccEntityRepository;
	@Autowired private ApplicationContext appContext;

	@Value("${messages.perminute}")
	private Integer messagesPerminute;

	private Map<String, DxccEntity> map = new HashMap<>();

	@Override
	public void complete() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			List<Email> emails = emailRepository.getAllWithLogfileByEdition(edition);
			if (emails.size() > messagesPerminute)
				emails = emails.subList(0, messagesPerminute);

			for (Email email : emails) {
				ContestLog contestLog = contestLogRepository.findByEmail(email);
				List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
				List<ContestQso> newQsos = contestQsos.stream().map(qso -> {
					DxccEntity dxccEntity = null;
					try {
						dxccEntity = getDxccOf(qso.getCallsignr(), edition);
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

	private DxccEntity getDxccOf(String callsign, Edition edition) throws FmreContestException {
		DxccEntity dxccEntity;
		
		//1st atempt, from de map.
		dxccEntity = map.get(callsign);
		if(dxccEntity != null)
			return dxccEntity;
		
		//2nd attempt, from de db
		List<DxccEntity> dxccEntities = dxccEntityRepository.getByCallsignOnEdition(callsign, edition);
		if(!dxccEntities.isEmpty()) {
			dxccEntity = dxccEntities.get(0);
			map.put(callsign, dxccEntity);
			return dxccEntity;
		}
		
		// 3rd attempt, from QRZ
		IDxccService dxccServiceQrz = appContext.getBean("dxccServiceQrzImpl", IDxccService.class);
		CallsignDAO res = dxccServiceQrz.getDxccFromCallsign(callsign);
		dxccEntity = QrzUtil.parse(res);
		if (dxccEntity != null) {
			dxccEntity = dxccEntityRepository.save(dxccEntity);
			map.put(callsign, dxccEntity);
			return dxccEntity;
		}

		return null;
	}
}
