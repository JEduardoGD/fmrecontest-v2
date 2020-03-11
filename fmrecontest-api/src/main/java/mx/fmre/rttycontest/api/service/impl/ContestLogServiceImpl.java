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
import mx.fmre.rttycontest.persistence.repository.IConteoRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;

@Service
public class ContestLogServiceImpl implements IContestLogService {

	@Autowired
	private IConteoRepository conteoRepository;
	@Autowired
	private IContestLogRepository contestLogRepository;

	@Override
	public List<ContestlogDto> findByConteoId(Integer conteoId) throws FmreContestException {
		Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
		List<ContestLog> contestLogList = contestLogRepository.findByConteo(conteo);
		return contestLogList.stream().map(x -> {
			ContestlogDto contestlogDto = new ContestlogDto();
			contestlogDto.setId(x.getId());
			contestlogDto.setEmailId(x.getEmail().getId());
			return contestlogDto;
		}).collect(Collectors.toList());
	}
}
