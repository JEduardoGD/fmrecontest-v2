package mx.fmre.rttycontest.bs.reports.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dxcc.service.ExternalDxccService;
import mx.fmre.rttycontest.bs.reports.service.IResultsReports;
import mx.fmre.rttycontest.bs.util.ResultReportsUtil;
import mx.fmre.rttycontest.bs.util.csv.CsvUtil;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.EmailEmailError;
import mx.fmre.rttycontest.persistence.model.LastEmail;
import mx.fmre.rttycontest.persistence.model.RelConteoContestLog;
import mx.fmre.rttycontest.persistence.repository.EmailEmailErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IConteoRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.ILastEmailRepository;
import mx.fmre.rttycontest.persistence.repository.IRelConteoContestLogRepository;

@Service
@Slf4j
public class ResultsReportsImpl implements IResultsReports {

	@Autowired private IConteoRepository conteoRepository;
	@Autowired private IContestLogRepository contestLogRepository;
	@Autowired private IRelConteoContestLogRepository relConteoContestLogRepository;
	@Autowired private ILastEmailRepository lastEmailRepository;
	@Autowired private EmailEmailErrorRepository emailEmailErrorRepository;
    @Autowired private ExternalDxccService      externalDxccService;

	private DxccEntity mexicoDxccEntity;
    
    @Value("${FMRE_CALLSIGN}")
    private String fmreCallsign;

	@PostConstruct
	private void init() {
        try {
            mexicoDxccEntity = externalDxccService.getDxccFromExternalServicesByEntityName("mexico");
            if (mexicoDxccEntity == null) {
                mexicoDxccEntity = externalDxccService.getDxccFromExternalServicesByCallsign(fmreCallsign);
            }
        } catch (FmreContestException e) {
            log.error(e.getLocalizedMessage());
        }
	}

	@Override
	public byte[] highPowerWorld(int conteoId) {
		List<RelConteoContestLog> listRelConteoContestLog = this.filterContestLogList(conteoId);

		// filter high power
		List<RelConteoContestLog> highPowerListRelConteoContestLog = ResultReportsUtil
				.filterHighPowerWorld(listRelConteoContestLog, contestLogRepository);

		List<String[]> listStringsContent = this.createListHighPowerWorld(highPowerListRelConteoContestLog);

		String[] header = { "id", "callsign", "dxcc_country", "state", "power", "total_points", "place" };

		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}

	private List<String[]> createListHighPowerWorld(List<RelConteoContestLog> highPowerListRelConteoContestLog) {

		List<String[]> listStringsContent = new ArrayList<>();

		int place = 0;
		long lastPoints = -1;

		for (RelConteoContestLog q : highPowerListRelConteoContestLog) {

			place = lastPoints == q.getTotalPoints() ? place : place + 1;

			Long contestLogId = q.getContestLog().getId();
			ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
			String[] content = { contestLog.getId() + "", contestLog.getCallsign(),
					contestLog.getDxccEntity().getEntityCode() + "",
					contestLog.getAddressStateProvince() == null ? "" : contestLog.getAddressStateProvince(),
					contestLog.getCategoryPower(), q.getTotalPoints() + "", place + "" };
			
			

			listStringsContent.add(content);

			lastPoints = q.getTotalPoints();
		}

		return listStringsContent;

	}

	@Override
	public byte[] lowPowerWorld(int conteoId) {
		List<RelConteoContestLog> listRelConteoContestLog = this.filterContestLogList(conteoId);

		// filter low power
		List<RelConteoContestLog> lowPowerListRelConteoContestLog = ResultReportsUtil
				.filterLowPowerWorld(listRelConteoContestLog, contestLogRepository);

		String[] header = { "id", "callsign", "dxcc_country", "state", "power", "total_points", "place" };

		List<String[]> listStringsContent = this.createListLowPowerWorld(lowPowerListRelConteoContestLog);

		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}

	private List<String[]> createListLowPowerWorld(List<RelConteoContestLog> lowPowerListRelConteoContestLog) {

		List<String[]> listStringsContent = new ArrayList<>();

		int place = 0;
		long lastPoints = -1;

		for (RelConteoContestLog q : lowPowerListRelConteoContestLog) {

			place = lastPoints == q.getTotalPoints() ? place : place + 1;

			Long contestLogId = q.getContestLog().getId();
			ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
			String[] content = { contestLog.getId() + "", contestLog.getCallsign(),
					contestLog.getDxccEntity().getEntityCode() + "",
					contestLog.getAddressStateProvince() == null ? "" : contestLog.getAddressStateProvince(),
					contestLog.getCategoryPower(), q.getTotalPoints() + "", place + "" };

			listStringsContent.add(content);

			lastPoints = q.getTotalPoints();
		}

		return listStringsContent;
	}

	@Override
	public byte[] highPowerMexico(int conteoId) {
		List<RelConteoContestLog> listRelConteoContestLog = this.filterContestLogList(conteoId);

		// filter low power, mexico
		List<RelConteoContestLog> lowPowerListRelConteoContestLog = ResultReportsUtil
				.filterHighPowerMexico(listRelConteoContestLog, contestLogRepository, mexicoDxccEntity);

		String[] header = { "id", "callsign", "dxcc_country", "state", "power", "total_points", "place" };

		List<String[]> listStringsContent = this.createListHighPowerMexico(lowPowerListRelConteoContestLog);

		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}

	private List<String[]> createListHighPowerMexico(List<RelConteoContestLog> lowPowerListRelConteoContestLog) {

		List<String[]> listStringsContent = new ArrayList<>();

		int place = 0;
		long lastPoints = -1;

		for (RelConteoContestLog q : lowPowerListRelConteoContestLog) {

			place = lastPoints == q.getTotalPoints() ? place : place + 1;

			Long contestLogId = q.getContestLog().getId();
			ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
			String[] content = { contestLog.getId() + "", contestLog.getCallsign(),
					contestLog.getDxccEntity().getEntityCode() + "",
					contestLog.getAddressStateProvince() == null ? "" : contestLog.getAddressStateProvince(),
					contestLog.getCategoryPower(), q.getTotalPoints() + "", place + "" };

			listStringsContent.add(content);

			lastPoints = q.getTotalPoints();
		}

		return listStringsContent;
	}

	@Override
	public byte[] lowPowerMexico(int conteoId) {
		List<RelConteoContestLog> listRelConteoContestLog = this.filterContestLogList(conteoId);

		// filter low power, mexico
		List<RelConteoContestLog> lowPowerListRelConteoContestLog = ResultReportsUtil
				.filterLowPowerMexico(listRelConteoContestLog, contestLogRepository, mexicoDxccEntity);

		String[] header = { "id", "callsign", "dxcc_country", "state", "power", "total_points", "place" };

		List<String[]> listStringsContent = this.createListLowPowerMexico(lowPowerListRelConteoContestLog);

		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}

	private List<String[]> createListLowPowerMexico(List<RelConteoContestLog> lowPowerListRelConteoContestLog) {
		List<String[]> listStringsContent = new ArrayList<>();

		int place = 0;
		long lastPoints = -1;

		for (RelConteoContestLog q : lowPowerListRelConteoContestLog) {

			place = lastPoints == q.getTotalPoints() ? place : place + 1;

			Long contestLogId = q.getContestLog().getId();
			ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
			String[] content = { contestLog.getId() + "", contestLog.getCallsign(),
					contestLog.getDxccEntity().getEntityCode() + "",
					contestLog.getAddressStateProvince() == null ? "" : contestLog.getAddressStateProvince(),
					contestLog.getCategoryPower(), q.getTotalPoints() + "", place + "" };

			listStringsContent.add(content);

			lastPoints = q.getTotalPoints();
		}

		return listStringsContent;
	}

	@Override
	public byte[] lowPowerByCountry(int conteoId) {
		List<RelConteoContestLog> listRelConteoContestLog = this.filterContestLogList(conteoId);

		List<ContestLog> contestLogList = listRelConteoContestLog.stream()
				.map(rcc -> contestLogRepository.findById(rcc.getContestLog().getId()).orElse(null))
				.collect(Collectors.toList());

		List<RelConteoContestLog> lowPowerListRelConteoContestLog = ResultReportsUtil
				.filterLowPowerByCountry(listRelConteoContestLog, contestLogRepository, mexicoDxccEntity);
        
        List<Long> repeatedDxccEntityCodes = contestLogList.stream()
                .map(ContestLog::getDxccEntity)
                .map(DxccEntity::getEntityCode)
                .collect(Collectors.toList());

        List<Long> dxccEntityCodes = new ArrayList<>();
        for (Long r : repeatedDxccEntityCodes) {
            if (!dxccEntityCodes.contains(r)) {
                dxccEntityCodes.add(r);
            }
        }

		List<String[]> listStringsContent = this.createListByCountry(contestLogList,
				lowPowerListRelConteoContestLog, dxccEntityCodes);

		String[] header = { "id", "callsign", "dxcc_country", "state", "power", "total_points", "place" };

		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}

	@Override
	public byte[] highPowerByCountry(int conteoId) {
		List<RelConteoContestLog> listRelConteoContestLog = this.filterContestLogList(conteoId);

		List<ContestLog> contestLogList = listRelConteoContestLog.stream()
				.map(rcc -> contestLogRepository.findById(rcc.getContestLog().getId()).orElse(null))
				.collect(Collectors.toList());

		List<RelConteoContestLog> highPowerListRelConteoContestLog = ResultReportsUtil.filterHighPowerByCountry(listRelConteoContestLog,
				contestLogRepository, mexicoDxccEntity);

        List<Long> repeatedDxccEntityCodes = contestLogList.stream()
                .map(ContestLog::getDxccEntity)
                .map(DxccEntity::getEntityCode)
                .collect(Collectors.toList());

        List<Long> dxccEntityCodes = new ArrayList<>();
        for (Long r : repeatedDxccEntityCodes) {
            if (!dxccEntityCodes.contains(r)) {
                dxccEntityCodes.add(r);
            }
        }
		
		List<String[]> listStringsContent = this.createListByCountry(contestLogList,
				highPowerListRelConteoContestLog, dxccEntityCodes);

		String[] header = { "id", "callsign", "dxcc_country", "state", "power", "total_points", "place" };

		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}

	private List<String[]> createListByCountry(List<ContestLog> contestLogList,
			List<RelConteoContestLog> highPowerListRelConteoContestLog, List<Long> dxccEntityCodes) {

		List<String[]> listStringsContent = new ArrayList<>();

		for (Long dxccEntity : dxccEntityCodes) {
			List<RelConteoContestLog> rccByDxccEntity = highPowerListRelConteoContestLog.stream().filter(rcc -> {
				long contestLogId = rcc.getContestLog().getId().longValue();
				ContestLog contestLog = contestLogList.stream().filter(cl -> cl.getId().longValue() == contestLogId)
						.findFirst().orElse(null);
				return contestLog.getDxccEntity().getEntityCode().equals(dxccEntity);
			}).collect(Collectors.toList());

			if (rccByDxccEntity.isEmpty())
				continue;

			rccByDxccEntity = rccByDxccEntity.stream().sorted((o1, o2) -> {
				if (o1.getTotalPoints() < o2.getTotalPoints())
					return 1;
				if (o1.getTotalPoints() > o2.getTotalPoints())
					return -1;
				return 0;
			}).collect(Collectors.toList());

			int place = 0;
			long lastPoints = -1;
			for (RelConteoContestLog q : rccByDxccEntity) {
				place = lastPoints == q.getTotalPoints() ? place : place + 1;

				Long contestLogId = q.getContestLog().getId();
				ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
				String[] content = { contestLog.getId() + "", contestLog.getCallsign(),
						contestLog.getDxccEntity().getEntityCode() + "",
						contestLog.getAddressStateProvince() == null ? "" : contestLog.getAddressStateProvince(),
						contestLog.getCategoryPower(), q.getTotalPoints() + "", place + "" };

				listStringsContent.add(content);

				lastPoints = q.getTotalPoints();
			}
		}

		return listStringsContent;
	}

	private List<RelConteoContestLog> filterContestLogList(Integer conteoId) {
		Conteo conteo = conteoRepository.findById(conteoId).orElse(null);

		Integer editionId = conteo.getEdition().getId();

		List<Integer> lasEmailContestLogList = lastEmailRepository.findByEditionId(editionId).stream()
				.map(LastEmail::getContestLogId).collect(Collectors.toList());

		List<RelConteoContestLog> listRelConteoContestLog = relConteoContestLogRepository.findByConteo(conteo).stream()
				.filter(rcl -> {
					for (Integer l : lasEmailContestLogList) {
						if (l.longValue() == rcl.getContestLog().getId().longValue())
							return true;
					}
					return false;
				}).filter(RelConteoContestLog::isComplete).collect(Collectors.toList());

		// filter complete
		listRelConteoContestLog = listRelConteoContestLog.stream().filter(RelConteoContestLog::isComplete)
				.collect(Collectors.toList());

		listRelConteoContestLog = listRelConteoContestLog.stream().filter(rccl -> {
			Long contestLogId = rccl.getContestLog().getId();
			ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
			Integer emailId = contestLog.getEmail().getId();
			List<EmailEmailError> listRelEmailEmailerror = emailEmailErrorRepository.findByEmailId(emailId);
			return listRelEmailEmailerror.size() <= 0;
		}).collect(Collectors.toList());

		return listRelConteoContestLog;
	}

	@Override
	public byte[] allResultsReport(int conteoId) {
		List<RelConteoContestLog> listRelConteoContestLog = this.filterContestLogList(conteoId);

		List<ContestLog> contestLogList = listRelConteoContestLog.stream()
				.map(rcc -> contestLogRepository.findById(rcc.getContestLog().getId()).orElse(null))
				.collect(Collectors.toList());
        
        List<Long> repeatedDxccEntityCodes = contestLogList.stream()
                .map(ContestLog::getDxccEntity)
                .map(DxccEntity::getEntityCode)
                .collect(Collectors.toList());

        List<Long> dxccEntityCodes = new ArrayList<>();
        for (Long r : repeatedDxccEntityCodes) {
            if (!dxccEntityCodes.contains(r)) {
                dxccEntityCodes.add(r);
            }
        }

		ArrayList<String[]> allResults = new ArrayList<>();
		int id = 202100001;

		{
			List<RelConteoContestLog> highPowerListRelConteoContestLog = ResultReportsUtil
					.filterHighPowerWorld(listRelConteoContestLog, contestLogRepository);
			List<String[]> listStringsContent = this.createListHighPowerWorld(highPowerListRelConteoContestLog);
			for (String[] r : listStringsContent) {
				String[] l = {
						id++ + "", 
						r[1], 
						/* r[2] */ null, 
						/*r[3]*/   null, 
						/*r[4]*/ "HIGH", 
						r[5], 
						r[6], 
						"2021", 
						r[1] + "-" + "2021" };
				allResults.add(l);
			}
		}

		{
			List<RelConteoContestLog> lowPowerListRelConteoContestLog = ResultReportsUtil
					.filterLowPowerWorld(listRelConteoContestLog, contestLogRepository);
			List<String[]> listStringsContent = this.createListLowPowerWorld(lowPowerListRelConteoContestLog);
			for (String[] r : listStringsContent) {
				String[] l = { 
						id++ + "", 
						r[1], 
						/* r[2] */ null, 
						/*r[3]*/   null, 
						/*r[4]*/   "LOW", 
						r[5], 
						r[6], "2021", 
						r[1] + "-" + "2021" };
				allResults.add(l);
			}
		}
		
		{
			List<RelConteoContestLog> highPowerListRelConteoContestLog = ResultReportsUtil
					.filterHighPowerMexico(listRelConteoContestLog, contestLogRepository, mexicoDxccEntity);
			List<String[]> listStringsContent = this.createListHighPowerMexico(highPowerListRelConteoContestLog);
			for (String[] r : listStringsContent) {
				String[] l = { 
						id++ + "", 
						r[1], 
						"50", 
						r[3], 
						/*r[4]*/   "HIGH", 
						r[5], 
						r[6], "2021", 
						r[1] + "-" + "2021" };
				allResults.add(l);
			}
		}
		
		{
			List<RelConteoContestLog> lowPowerListRelConteoContestLog = ResultReportsUtil
					.filterLowPowerMexico(listRelConteoContestLog, contestLogRepository, mexicoDxccEntity);
			List<String[]> listStringsContent = this.createListLowPowerMexico(lowPowerListRelConteoContestLog);
			for (String[] r : listStringsContent) {
				String[] l = { 
						id++ + "", 
						r[1], 
						"50", 
						r[3], 
						/*r[4]*/   "LOW", 
						r[5], 
						r[6], "2021", 
						r[1] + "-" + "2021" };
				allResults.add(l);
			}
		}
		
		{
			List<RelConteoContestLog> highPowerListRelConteoContestLog = ResultReportsUtil
					.filterHighPowerByCountry(listRelConteoContestLog, contestLogRepository, mexicoDxccEntity);
			List<String[]> listStringsContent = this.createListByCountry(contestLogList, highPowerListRelConteoContestLog, dxccEntityCodes);
			for (String[] r : listStringsContent) {
				String[] l = { 
						id++ + "", 
						r[1], 
						r[2], 
						null, 
						/*r[4]*/   "HIGH", 
						r[5], 
						r[6], "2021", 
						r[1] + "-" + "2021" };
				allResults.add(l);
			}
		}
		
		{
			List<RelConteoContestLog> lowPowerListRelConteoContestLog = ResultReportsUtil
					.filterLowPowerByCountry(listRelConteoContestLog, contestLogRepository, mexicoDxccEntity);
			List<String[]> listStringsContent = this.createListByCountry(contestLogList, lowPowerListRelConteoContestLog, dxccEntityCodes);
			for (String[] r : listStringsContent) {
				String[] l = { 
						id++ + "", 
						r[1], 
						r[2], 
						null, 
						/*r[4]*/   "LOW", 
						r[5], 
						r[6], "2021", 
						r[1] + "-" + "2021" };
				allResults.add(l);
			}
		}
		
		String[] header = { 
				"id",
				"callsign",
				"dxcc_country",
				"state",
				"power",
				"total_points",
				"place",
				"ano",
				"REF"};
		
		
		return CsvUtil.createCsvByteArray(header, allResults);

	}
}
