package mx.fmre.rttycontest.bs.reports.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.reports.service.IResultsReports;
import mx.fmre.rttycontest.bs.util.CollectiosUtil;
import mx.fmre.rttycontest.bs.util.csv.CsvUtil;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.EmailEmailError;
import mx.fmre.rttycontest.persistence.model.LastEmail;
import mx.fmre.rttycontest.persistence.model.RelConteoContestLog;
import mx.fmre.rttycontest.persistence.repository.EmailEmailErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IConteoRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;
import mx.fmre.rttycontest.persistence.repository.ILastEmailRepository;
import mx.fmre.rttycontest.persistence.repository.IRelConteoContestLogRepository;

@Service
public class ResultsReportsImpl implements IResultsReports {
	
	@Autowired private IConteoRepository conteoRepository;
	@Autowired private IContestLogRepository contestLogRepository;
	@Autowired private IRelConteoContestLogRepository relConteoContestLogRepository;
	@Autowired private ILastEmailRepository lastEmailRepository;
	@Autowired private EmailEmailErrorRepository emailEmailErrorRepository;
	@Autowired private IDxccEntityRepository dxccEntityRepository;
	
	private DxccEntity mexicoDxccEntity;
	
	@PostConstruct
	private void init() {
		this.mexicoDxccEntity = dxccEntityRepository.findById(50l).orElse(null);
	}

	@Override
	public byte[] highPowerWorld(int conteoId) {
		List<RelConteoContestLog> listRelConteoContestLog = this.filterContestLogList(conteoId);
		
		//filter high power
		List<RelConteoContestLog> highPowerListRelConteoContestLog = listRelConteoContestLog
				.stream()
				.filter(rcc -> {
					Long contestLogId = rcc.getContestLog().getId();
					ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
					return "HIGH".equals(contestLog.getCategoryPower());
					})
				.collect(Collectors.toList());
		
		highPowerListRelConteoContestLog = highPowerListRelConteoContestLog
				.stream()
				.sorted((o1,o2)->  {
					if(o1.getTotalPoints() < o2.getTotalPoints())
						return 1;
					if(o1.getTotalPoints() > o2.getTotalPoints())
						return -1;
					return 0;
				})
				.collect(Collectors.toList());
		
		String[] header = { 
				"id",
				"callsign",
				"dxcc_country",
				"state",
				"power",
				"total_points",
				"place"};
		
		List<String[]> listStringsContent = new ArrayList<>();
		
		int place = 0;
		long lastPoints = -1;
		
		for (RelConteoContestLog q : highPowerListRelConteoContestLog) {
			
			place = lastPoints == q.getTotalPoints() ? place : place + 1;
			
//			Integer conteoId = q.getConteo().getId();
//			Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
			Long contestLogId = q.getContestLog().getId();
			ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
			String[] content = {
					contestLog.getId() + "",
					contestLog.getCallsign(),
					contestLog.getDxccEntity().getId() + "",
					contestLog.getAddressStateProvince() == null ? "" : contestLog.getAddressStateProvince(),
					contestLog.getCategoryPower(),
					q.getTotalPoints() + "",
					place + ""};

			listStringsContent.add(content);
			
			lastPoints = q.getTotalPoints();
		}
		
		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}

	@Override
	public byte[] lowPowerWorld(int conteoId) {
		List<RelConteoContestLog> listRelConteoContestLog = this.filterContestLogList(conteoId);
		
		//filter low power
		List<RelConteoContestLog> lowPowerListRelConteoContestLog = listRelConteoContestLog
				.stream()
				.filter(rcc -> {
					Long contestLogId = rcc.getContestLog().getId();
					ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
					return "LOW".equals(contestLog.getCategoryPower());
					})
				.collect(Collectors.toList());
		
		lowPowerListRelConteoContestLog = lowPowerListRelConteoContestLog
				.stream()
				.sorted((o1,o2)->  {
					if(o1.getTotalPoints() < o2.getTotalPoints())
						return 1;
					if(o1.getTotalPoints() > o2.getTotalPoints())
						return -1;
					return 0;
				})
				.collect(Collectors.toList());
		
		String[] header = { 
				"id",
				"callsign",
				"dxcc_country",
				"state",
				"power",
				"total_points",
				"place"};
		
		List<String[]> listStringsContent = new ArrayList<>();
		
		int place = 0;
		long lastPoints = -1;
		
		for (RelConteoContestLog q : lowPowerListRelConteoContestLog) {
			
			place = lastPoints == q.getTotalPoints() ? place : place + 1;
			
//			Integer conteoId = q.getConteo().getId();
//			Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
			Long contestLogId = q.getContestLog().getId();
			ContestLog contestLog = contestLogRepository
					.findById(contestLogId)
					.orElse(null);
			String[] content = {
					contestLog.getId() + "",
					contestLog.getCallsign(),
					contestLog.getDxccEntity().getId() + "",
					contestLog.getAddressStateProvince() == null ? "" : contestLog.getAddressStateProvince(),
					contestLog.getCategoryPower(),
					q.getTotalPoints() + "",
					place + ""};

			listStringsContent.add(content);
			
			lastPoints = q.getTotalPoints();
		}
		
		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}

	@Override
	public byte[] highPowerMexico(int conteoId) {
		List<RelConteoContestLog> listRelConteoContestLog = this.filterContestLogList(conteoId);
		
		//filter low power
		List<RelConteoContestLog> lowPowerListRelConteoContestLog = listRelConteoContestLog
				.stream()
				.filter(rcc -> {
					Long contestLogId = rcc.getContestLog().getId();
					ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
					return ("HIGH".equals(contestLog.getCategoryPower()) &&
							contestLog.getDxccEntity().getId().longValue() == mexicoDxccEntity.getId().longValue());
					})
				.collect(Collectors.toList());
		
		lowPowerListRelConteoContestLog = lowPowerListRelConteoContestLog
				.stream()
				.sorted((o1,o2)->  {
					if(o1.getTotalPoints() < o2.getTotalPoints())
						return 1;
					if(o1.getTotalPoints() > o2.getTotalPoints())
						return -1;
					return 0;
				})
				.collect(Collectors.toList());
		
		String[] header = { 
				"id",
				"callsign",
				"dxcc_country",
				"state",
				"power",
				"total_points",
				"place"};
		
		List<String[]> listStringsContent = new ArrayList<>();
		
		int place = 0;
		long lastPoints = -1;
		
		for (RelConteoContestLog q : lowPowerListRelConteoContestLog) {
			
			place = lastPoints == q.getTotalPoints() ? place : place + 1;
			
//			Integer conteoId = q.getConteo().getId();
//			Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
			Long contestLogId = q.getContestLog().getId();
			ContestLog contestLog = contestLogRepository
					.findById(contestLogId)
					.orElse(null);
			String[] content = {
					contestLog.getId() + "",
					contestLog.getCallsign(),
					contestLog.getDxccEntity().getId() + "",
					contestLog.getAddressStateProvince() == null ? "" : contestLog.getAddressStateProvince(),
					contestLog.getCategoryPower(),
					q.getTotalPoints() + "",
					place + ""};

			listStringsContent.add(content);
			
			lastPoints = q.getTotalPoints();
		}
		
		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}

	@Override
	public byte[] lowPowerMexico(int conteoId) {
		List<RelConteoContestLog> listRelConteoContestLog = this.filterContestLogList(conteoId);
		
		//filter low power
		List<RelConteoContestLog> lowPowerListRelConteoContestLog = listRelConteoContestLog
				.stream()
				.filter(rcc -> {
					Long contestLogId = rcc.getContestLog().getId();
					ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
					return ("LOW".equals(contestLog.getCategoryPower()) &&
							contestLog.getDxccEntity().getId().longValue() == mexicoDxccEntity.getId().longValue());
					})
				.collect(Collectors.toList());
		
		lowPowerListRelConteoContestLog = lowPowerListRelConteoContestLog
				.stream()
				.sorted((o1,o2)->  {
					if(o1.getTotalPoints() < o2.getTotalPoints())
						return 1;
					if(o1.getTotalPoints() > o2.getTotalPoints())
						return -1;
					return 0;
				})
				.collect(Collectors.toList());
		
		String[] header = { 
				"id",
				"callsign",
				"dxcc_country",
				"state",
				"power",
				"total_points",
				"place"};
		
		List<String[]> listStringsContent = new ArrayList<>();
		
		int place = 0;
		long lastPoints = -1;
		
		for (RelConteoContestLog q : lowPowerListRelConteoContestLog) {
			
			place = lastPoints == q.getTotalPoints() ? place : place + 1;
			
//			Integer conteoId = q.getConteo().getId();
//			Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
			Long contestLogId = q.getContestLog().getId();
			ContestLog contestLog = contestLogRepository
					.findById(contestLogId)
					.orElse(null);
			String[] content = {
					contestLog.getId() + "",
					contestLog.getCallsign(),
					contestLog.getDxccEntity().getId() + "",
					contestLog.getAddressStateProvince() == null ? "" : contestLog.getAddressStateProvince(),
					contestLog.getCategoryPower(),
					q.getTotalPoints() + "",
					place + ""};

			listStringsContent.add(content);
			
			lastPoints = q.getTotalPoints();
		}
		
		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}
	
	@Override
	public byte[] lowPowerByCountry(int conteoId) {
		List<RelConteoContestLog> listRelConteoContestLog = this.filterContestLogList(conteoId);
		
		List<ContestLog> contestLogList = listRelConteoContestLog
				.stream()
				.map(rcc -> contestLogRepository.findById(rcc.getContestLog().getId()).orElse(null))
				.collect(Collectors.toList());
		
		List<DxccEntity> distinctDxccEntity = contestLogList
				.stream()
				.filter(CollectiosUtil.distinctByKey(ContestLog::getDxccEntity))
				.map(ContestLog::getDxccEntity)
				.collect(Collectors.toList());
		
		//filter low power
		List<RelConteoContestLog> lowPowerListRelConteoContestLog = listRelConteoContestLog
				.stream()
				.filter(rcc -> {
					long contestLogId = rcc.getContestLog().getId().longValue();
					ContestLog contestLog = contestLogList
							.stream()
							.filter(cl -> cl.getId().longValue() == contestLogId)
							.findFirst()
							.orElse(null);
					return ("LOW".equals(contestLog.getCategoryPower()));
				})
				.collect(Collectors.toList());
		
		List<String[]> listStringsContent = new ArrayList<>();
		
		for (DxccEntity dxccEntity : distinctDxccEntity) {
			List<RelConteoContestLog> rccByDxccEntity = lowPowerListRelConteoContestLog
					.stream()
					.filter(rcc -> {
						long contestLogId = rcc.getContestLog().getId().longValue();
						ContestLog contestLog = contestLogList
								.stream()
								.filter(cl -> cl.getId().longValue() == contestLogId)
								.findFirst()
								.orElse(null);
						return contestLog.getDxccEntity().getId().longValue() == dxccEntity.getId().longValue();
					})
					.collect(Collectors.toList());
					
			if(rccByDxccEntity.isEmpty())
				continue;

			
			rccByDxccEntity = rccByDxccEntity
					.stream()
					.sorted((o1,o2)->  {
						if(o1.getTotalPoints() < o2.getTotalPoints())
							return 1;
						if(o1.getTotalPoints() > o2.getTotalPoints())
							return -1;
						return 0;
					})
					.collect(Collectors.toList());
			

			int place = 0;
			long lastPoints = -1;
			for (RelConteoContestLog q : rccByDxccEntity) {
				place = lastPoints == q.getTotalPoints() ? place : place + 1;
				
//				Integer conteoId = q.getConteo().getId();
//				Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
				Long contestLogId = q.getContestLog().getId();
				ContestLog contestLog = contestLogRepository
						.findById(contestLogId)
						.orElse(null);
				String[] content = {
						contestLog.getId() + "",
						contestLog.getCallsign(),
						contestLog.getDxccEntity().getId() + "",
						contestLog.getAddressStateProvince() == null ? "" : contestLog.getAddressStateProvince(),
						contestLog.getCategoryPower(),
						q.getTotalPoints() + "",
						place + ""};

				listStringsContent.add(content);
				
				lastPoints = q.getTotalPoints();
			}
			
		}
		
		String[] header = { 
				"id",
				"callsign",
				"dxcc_country",
				"state",
				"power",
				"total_points",
				"place"};
		
		
		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}

	private List<RelConteoContestLog> filterContestLogList(Integer conteoId) {
		Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
		
		Integer editionId = conteo.getEdition().getId();
		
		List<Integer> lasEmailContestLogList = lastEmailRepository
				.findByEditionId(editionId)
				.stream()
				.map(LastEmail::getContestLogId)
				.collect(Collectors.toList());
		
		
		List<RelConteoContestLog> listRelConteoContestLog = relConteoContestLogRepository
				.findByConteo(conteo)
				.stream()
				.filter(rcl -> {
					for (Integer l : lasEmailContestLogList) {
						if (l.longValue() == rcl.getContestLog().getId().longValue())
							return true;
					}
					return false;
				})
				.filter(RelConteoContestLog::isComplete)
				.collect(Collectors.toList());
		
		//filter complete
		listRelConteoContestLog = listRelConteoContestLog
				.stream()
				.filter(RelConteoContestLog::isComplete)
				.collect(Collectors.toList());
		
		listRelConteoContestLog = listRelConteoContestLog
				.stream()
				.filter(rccl -> {
					Long contestLogId = rccl.getContestLog().getId();
					ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
					Integer emailId = contestLog.getEmail().getId();
					List<EmailEmailError> listRelEmailEmailerror = emailEmailErrorRepository.findByEmailId(emailId);
					return listRelEmailEmailerror.size() <= 0;})
				.collect(Collectors.toList());
		
		return listRelConteoContestLog;
	}

}
