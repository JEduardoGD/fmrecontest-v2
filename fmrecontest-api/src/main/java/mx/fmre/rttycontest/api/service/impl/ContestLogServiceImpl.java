package mx.fmre.rttycontest.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.dto.ContestlogDto;
import mx.fmre.rttycontest.api.service.IContestLogService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.RelConteoContestLog;
import mx.fmre.rttycontest.persistence.repository.IConteoRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IRelConteoContestLogRepository;

@Service
public class ContestLogServiceImpl implements IContestLogService {
	@Autowired private IConteoRepository conteoRepository;
	@Autowired private IRelConteoContestLogRepository relConteoContestLogRepository;
	@Autowired private IContestLogRepository contestLogRepository;
	@Autowired private IContestQsoRepository contestQsoRepository;
	
	@Override
	public List<ContestlogDto> findByConteoId(Integer conteoId) throws FmreContestException {
		Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
		List<RelConteoContestLog> relConteoContestLogs = relConteoContestLogRepository.findByConteo(conteo);
		
		return relConteoContestLogs.stream().map(x -> {
			ContestLog contestLog = contestLogRepository.findById(x.getContestLog().getId())
					.orElse(null);
			
			Integer emailId = contestLog.getEmail().getId();

			List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
			int contestQsoSize = contestQsos.size();
			
			ContestlogDto contestlogDto = new  ContestlogDto();
			contestlogDto.setId(contestLog.getId());
			contestlogDto.setEmailId(emailId);
			contestlogDto.setCallsign(contestLog.getCallsign());
			contestlogDto.setContestQsoSize(contestQsoSize);
			contestlogDto.setSumOfPoints(x.getSumOfPoints());
			contestlogDto.setMultipliers(x.getMultipliers());
			contestlogDto.setTotalPoints(x.getTotalPoints());
			contestlogDto.setComplete(x.isComplete());
			return contestlogDto;
		}).collect(Collectors.toList());
	}
}
