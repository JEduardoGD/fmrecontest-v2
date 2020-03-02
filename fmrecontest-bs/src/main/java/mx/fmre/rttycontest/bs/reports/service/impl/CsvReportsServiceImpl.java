package mx.fmre.rttycontest.bs.reports.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.reports.service.ICsvReportsService;
import mx.fmre.rttycontest.bs.util.csv.CsvUtil;
import mx.fmre.rttycontest.persistence.model.CatBand;
import mx.fmre.rttycontest.persistence.model.CatEmailError;
import mx.fmre.rttycontest.persistence.model.CatQsoError;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.EmailStatus;
import mx.fmre.rttycontest.persistence.model.LastEmail;
import mx.fmre.rttycontest.persistence.model.RelConteoContestLog;
import mx.fmre.rttycontest.persistence.model.RelQsoConteo;
import mx.fmre.rttycontest.persistence.model.RelQsoConteoQsoError;
import mx.fmre.rttycontest.persistence.repository.ICatBandRepository;
import mx.fmre.rttycontest.persistence.repository.ICatEmailErrorRepository;
import mx.fmre.rttycontest.persistence.repository.ICatQsoErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IConteoRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailEstatusRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.persistence.repository.ILastEmailRepository;
import mx.fmre.rttycontest.persistence.repository.IRelConteoContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IRelQsoConteoQsoErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IRelQsoConteoRepository;

@Service
public class CsvReportsServiceImpl implements ICsvReportsService {

	@Autowired private IConteoRepository conteoRepository;
	@Autowired private IEditionRepository editionRepository;
	@Autowired private IRelConteoContestLogRepository relConteoContestLogRepository;
	@Autowired private IContestLogRepository contestLogRepository;
	@Autowired private IContestQsoRepository contestQsoRepository;
	@Autowired private IEmailRepository emailRepository;
	@Autowired private IEmailEstatusRepository emailEstatusRepository;
	@Autowired private ICatEmailErrorRepository catEmailErrorRepository;
	@Autowired private IRelQsoConteoRepository relQsoConteoRepository;
	@Autowired private IDxccEntityRepository dxccEntityRepository;
	@Autowired private IRelQsoConteoQsoErrorRepository relQsoConteoQsoErrorRepository;
	@Autowired private ICatQsoErrorRepository catQsoErrorRepository;
	@Autowired private ICatBandRepository catBandRepository;
	@Autowired private ILastEmailRepository lastEmailRepository;
	
	private Map<Integer, String> emailStatusesArray;
	private Map<Integer, String> mapEmmailError;
	private List<DxccEntity> listDxccEntities;
	private List<CatQsoError> listCatQsoError;
	private List<CatBand> listBands;
	
	private DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	@PostConstruct
	private void initData() {
		this.emailStatusesArray = emailEstatusRepository
				.findAll()
				.stream()
				.collect(Collectors.toMap(EmailStatus::getId, EmailStatus::getStatus));
		
		this.mapEmmailError = catEmailErrorRepository
				.findAll()
				.stream()
				.collect(Collectors.toMap(CatEmailError::getId, CatEmailError::getDescripcion));
		
		this.listDxccEntities = dxccEntityRepository.findAll();
		
		this.listCatQsoError = catQsoErrorRepository.findAll();
		
		this.listBands = catBandRepository.findAll();
	}
	
	@Override
	public byte[] getAllEmailsOnByEditionId(int editionId) {
		
		Edition edition = editionRepository.findById(editionId).orElse(null);
		List<Email> emails = emailRepository.findByEdition(edition);
		
		List<LastEmail> lastEmails = lastEmailRepository.findByEditionId(editionId);
		
		String[] header = { 
				"ID",
				"SUBJECT",
				"FROM NAME",
				"FROM ADDRESS",
				"SENT AT UTC",
				"VERIFIED AT UTC",
				"ANSWERED AT UTC", 
				"STATUS",
				"USED ON COUNT",
				"ERRORS"};
		
		List<String[]> listStringsContent = CsvUtil.listEmailsToStrings(emails, lastEmails, catEmailErrorRepository, mapEmmailError, emailStatusesArray);
		
		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}

	@Override
	public byte[] generateConteoReport(int conteoId) {
		Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
		List<RelConteoContestLog> relConteoContestLogs = relConteoContestLogRepository.findByConteo(conteo);
		
		String[] header = { 
				"EMAIL ID", 
				"LOG ID", 
				"CALLSIGN", 
				"# QSOS", 
				"POINTS", 
				"MULTIPLIERS", 
				"TOTAL", 
				"USED FOR COUNT", 
				"" };
		
		List<String[]> listStringsContent = new ArrayList<>();

		for (RelConteoContestLog relConteoContestLog : relConteoContestLogs) {
			ContestLog contestLog = contestLogRepository.findById(relConteoContestLog.getContestLog().getId())
					.orElse(null);
			
			Integer emailId = contestLog.getEmail().getId();

			List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
			int contestQsoSize = contestQsos.size();

			String[] content = {
					emailId + "",
					contestLog.getId() + "",
					contestLog.getCallsign(),
					contestQsoSize + "",
					relConteoContestLog.getSumOfPoints() + "",
					relConteoContestLog.getMultipliers() + "",
					relConteoContestLog.getTotalPoints() + "",
					relConteoContestLog.isComplete() ? "" : "ERROR"};

			listStringsContent.add(content);
		}
		
		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}

	@Override
	public byte[] generateLogReport(int conteoId, long logId) {
		ContestLog contestLog = contestLogRepository.findById(logId).orElse(null);
		List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
		
		Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
		
		String[] header = {
				"QSO ID",
				"FREQUENCY",
				"CALL E",
				"CALL R",
				"DATETIME",
				"EXCHANGE E",
				"EXCHANGE R",
				"RST E",
				"RST R",
				"DXCC NOT FOUND",
				"DXCC ENTITY",
				"POINTS",
				"IS MULTIPLY",
				"BAND NOT FOUND",
				"BAND",
				"ERRORS"};
		
		List<String[]> listStringsContent = new ArrayList<>();

		for (ContestQso qso : contestQsos) {
			
			RelQsoConteo relQsoConteo = relQsoConteoRepository.findByContestQsoAndConteo(qso, conteo);
			
			Long dxccEntityId = qso.getDxccEntity() != null ? qso.getDxccEntity().getId() : null;
			DxccEntity dxccEntity = null;
			if(dxccEntityId != null) {
				dxccEntity = this.listDxccEntities
						.stream()
						.filter(x -> x.getId().longValue() == dxccEntityId.longValue())
						.findFirst()
						.orElse(null);
			}
			
			List<RelQsoConteoQsoError> relQsoConteoQsoErrors = relQsoConteoQsoErrorRepository.findByRelQsoConteo(relQsoConteo);
			
			List<Integer> idsQsoErrors = relQsoConteoQsoErrors.stream().map(x -> x.getCatQsoError().getId()).collect(Collectors.toList());
			
			List<CatQsoError> qsosErrors = listCatQsoError.stream().filter(x  -> idsQsoErrors.contains(x.getId())).collect(Collectors.toList());
			
			List<String> keyErrors = qsosErrors.stream().map(r -> r.getKey()).collect(Collectors.toList());
			
			CatBand qsoBand = null;
			if(qso.getBand() != null) {
				Integer qsoBandId = qso.getBand().getId();
				qsoBand = listBands
						.stream()
						.filter(x -> x.getId() == qsoBandId)
						.findFirst()
						.orElse(null);
			}
			
			String[] content = {
					qso.getId() + "",
					qso.getFrequency() + "",
					contestLog.getCallsign(),
					qso.getCallsignr(),
					df.format(qso.getDatetime()),
					qso.getExchangee(),
					qso.getExchanger(),
					qso.getRste(),
					qso.getRstr(),
					qso.getDxccNotFound() ? "NOT FOUND" : "",
					dxccEntity != null ? (String.format("(%d) %s", dxccEntity.getId(), dxccEntity.getEntity())) : "",
					relQsoConteo.getPoints() != null ? relQsoConteo.getPoints()  + "" : "",
					relQsoConteo.isMultiply() ? "1" : "",
					qsoBand == null ? "NOT FOUND" : "",
					qsoBand != null ? qsoBand.getBand() : "",
					String.join(";", keyErrors)
			};

			listStringsContent.add(content);
		}
		
		return CsvUtil.createCsvByteArray(header, listStringsContent);
	}
}
