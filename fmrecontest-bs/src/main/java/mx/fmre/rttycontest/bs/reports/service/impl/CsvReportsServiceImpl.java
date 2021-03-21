package mx.fmre.rttycontest.bs.reports.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.log.service.IBsLogService;
import mx.fmre.rttycontest.bs.qso.service.IBsQsosService;
import mx.fmre.rttycontest.bs.reports.service.ICsvReportsService;
import mx.fmre.rttycontest.bs.util.csv.CsvUtil;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.AttachedFile;
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
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailEstatusRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.persistence.repository.ILastEmailRepository;
import mx.fmre.rttycontest.persistence.repository.IRelConteoContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IRelQsoConteoQsoErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IRelQsoConteoRepository;

@Service
public class CsvReportsServiceImpl implements ICsvReportsService {

    @Autowired
    private IConteoRepository conteoRepository;
    @Autowired
    private IEditionRepository editionRepository;
    @Autowired
    private IRelConteoContestLogRepository relConteoContestLogRepository;
    @Autowired
    private IContestLogRepository contestLogRepository;
    @Autowired
    private IContestQsoRepository contestQsoRepository;
    @Autowired
    private IEmailRepository emailRepository;
    @Autowired
    private IEmailEstatusRepository emailEstatusRepository;
    @Autowired
    private ICatEmailErrorRepository catEmailErrorRepository;
    @Autowired
    private IRelQsoConteoRepository relQsoConteoRepository;
    @Autowired
    private IRelQsoConteoQsoErrorRepository relQsoConteoQsoErrorRepository;
    @Autowired
    private ICatQsoErrorRepository catQsoErrorRepository;
    @Autowired
    private ICatBandRepository catBandRepository;
    @Autowired
    private ILastEmailRepository lastEmailRepository;
    @Autowired
    private IBsQsosService qsosService;
    @Autowired
    private IBsLogService logService;

    private Map<Integer, String> emailStatusesArray;
    private Map<Integer, String> mapEmmailError;
    private List<CatQsoError> listCatQsoError;
    private List<CatBand> listBands;

    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @PostConstruct
    private void initData() {
        this.emailStatusesArray = emailEstatusRepository.findAll().stream()
                .collect(Collectors.toMap(EmailStatus::getId, EmailStatus::getStatus));

        this.mapEmmailError = catEmailErrorRepository.findAll().stream()
                .collect(Collectors.toMap(CatEmailError::getId, CatEmailError::getDescripcion));

        this.listCatQsoError = catQsoErrorRepository.findAll();

        this.listBands = catBandRepository.findAll();
    }

    @Override
    public byte[] getAllEmailsOnByEditionId(int editionId) {

        Edition edition = editionRepository.findById(editionId).orElse(null);
        List<Email> emails = emailRepository.findByEdition(edition);

        List<LastEmail> lastEmails = lastEmailRepository.findByEditionId(editionId);

        String[] header = { "ID", "EMAIL COUNT", "SUBJECT", "FROM NAME", "FROM ADDRESS", "SENT AT UTC",
                "VERIFIED AT UTC", "ANSWERED AT UTC", "STATUS", "USED ON COUNT", "ERRORS" };

        List<String[]> listStringsContent = CsvUtil.listEmailsToStrings(emails, lastEmails, catEmailErrorRepository,
                mapEmmailError, emailStatusesArray);

        return CsvUtil.createCsvByteArray(header, listStringsContent);
    }

    @Override
    public byte[] generateConteoReport(int conteoId) {
        Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
        List<RelConteoContestLog> relConteoContestLogs = relConteoContestLogRepository.findByConteo(conteo);

        String[] header = { "EMAIL ID", "LOG ID", "CALLSIGN", "# QSOS", "POINTS", "MULTIPLIERS", "TOTAL", "" };

        List<String[]> listStringsContent = new ArrayList<>();

        for (RelConteoContestLog relConteoContestLog : relConteoContestLogs) {
            ContestLog contestLog = contestLogRepository.findById(relConteoContestLog.getContestLog().getId())
                    .orElse(null);

            Integer emailId = contestLog.getEmail().getId();

            List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
            int contestQsoSize = contestQsos.size();

            String[] content = { emailId + "", contestLog.getId() + "", contestLog.getCallsign(), contestQsoSize + "",
                    relConteoContestLog.getSumOfPoints() + "", relConteoContestLog.getMultipliers() + "",
                    relConteoContestLog.getTotalPoints() + "", relConteoContestLog.isComplete() ? "" : "ERROR" };

            listStringsContent.add(content);
        }

        return CsvUtil.createCsvByteArray(header, listStringsContent);
    }

    @Override
    public byte[] generateLogReport(int conteoId, long logId) {
        ContestLog contestLog = contestLogRepository.findById(logId).orElse(null);
        List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);

        Conteo conteo = conteoRepository.findById(conteoId).orElse(null);

        String[] header = { "QSO ID", "FREQUENCY", "CALL E", "CALL R", "DATETIME", "EXCHANGE E", "EXCHANGE R", "RST E",
                "RST R", "DXCC NOT FOUND", "DXCC ENTITY", "POINTS", "IS MULTIPLY", "BAND NOT FOUND", "BAND", "ERRORS" };

        List<String[]> listStringsContent = new ArrayList<>();

        for (ContestQso qso : contestQsos) {

            RelQsoConteo relQsoConteo = relQsoConteoRepository.findByContestQsoAndConteo(qso, conteo);

            DxccEntity dxccEntity = qso.getDxccEntity();

            List<RelQsoConteoQsoError> relQsoConteoQsoErrors = relQsoConteoQsoErrorRepository
                    .findByRelQsoConteo(relQsoConteo);

            List<Integer> idsQsoErrors = relQsoConteoQsoErrors.stream().filter(x -> x.getCatQsoError() != null)
                    .map(x -> x.getCatQsoError().getId()).collect(Collectors.toList());

            List<CatQsoError> qsosErrors = listCatQsoError.stream().filter(x -> idsQsoErrors.contains(x.getId()))
                    .collect(Collectors.toList());

            List<String> keyErrors = qsosErrors.stream().map(r -> r.getKey()).collect(Collectors.toList());

            CatBand qsoBand = null;
            if (qso.getBand() != null) {
                Integer qsoBandId = qso.getBand().getId();
                qsoBand = listBands.stream().filter(x -> x.getId() == qsoBandId).findFirst().orElse(null);
            }

            String[] content = { qso.getId() + "", qso.getFrequency() + "", contestLog.getCallsign(),
                    qso.getCallsignr(), df.format(qso.getDatetime()), qso.getExchangee(), qso.getExchanger(),
                    qso.getRste(), qso.getRstr(),
                    (qso.getDxccNotFound() != null && qso.getDxccNotFound() == true) ? "NOT FOUND" : "",
                    dxccEntity != null ? (String.format("(%d) %s", dxccEntity.getId(), dxccEntity.getEntity())) : "",
                    relQsoConteo.getPoints() != null ? relQsoConteo.getPoints() + "" : "",
                    relQsoConteo.isMultiply() ? "1" : "", qsoBand == null ? "NOT FOUND" : "",
                    qsoBand != null ? qsoBand.getBand() : "", String.join(";", keyErrors) };

            listStringsContent.add(content);
        }

        return CsvUtil.createCsvByteArray(header, listStringsContent);
    }

    @Override
    public byte[] getCallsignWithoutDxccEntityReport(int editionId) {
        Edition edition = editionRepository.findById(editionId).orElse(null);
        List<Email> emails = emailRepository.findByEdition(edition);

        List<LastEmail> lastEmails = lastEmailRepository.findByEditionId(editionId);

        List<Integer> lastEmailsIdList = lastEmails.stream().map(e -> e.getEmailId()).collect(Collectors.toList());

        List<Email> filteredEmails = emails.stream().filter(e -> lastEmailsIdList.contains(e.getId()))
                .collect(Collectors.toList());

        Map<String, Integer> map = new HashMap<>();

        for (Email email : filteredEmails) {
            ContestLog contestLog = contestLogRepository.findByEmail(email);
            List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
            qsos = qsos.stream().filter(q -> (q.getError() == null || q.getError().booleanValue() == false))
                    .filter(q -> q.getDxccEntity() == null).collect(Collectors.toList());

            for (ContestQso qso : qsos) {
                int c = 1;
                if (map.containsKey(qso.getCallsignr())) {
                    c = map.get(qso.getCallsignr());
                    c = c + 1;
                }
                map.put(qso.getCallsignr(), c);
            }
        }

        String[] header = { "DXCC ENTIY", "COUNT" };

        List<String[]> listStringsContent = new ArrayList<>();
        for (String key : map.keySet()) {
            Integer count = map.get(key);
            listStringsContent.add(new String[] { key, count + "" });
        }

        return CsvUtil.createCsvByteArray(header, listStringsContent);

    }

    @Override
    public byte[] getCallersByEntity(int editionId) throws FmreContestException {
        List<ContestLog> logs = logService.getValidLogsOfEdition(editionId);

        Map<DxccEntity, Integer> counting = new HashMap<>();

        for (ContestLog log : logs) {
            DxccEntity dxccEntity = log.getDxccEntity();
            if (counting.containsKey(dxccEntity)) {
                counting.put(dxccEntity, counting.get(dxccEntity) + 1);
            } else {
                counting.put(dxccEntity, 1);
            }
        }

        String[] header = { "DXCC ENTIY", "DXCC ENTITY NAME", "COUNT" };
        List<String[]> listStringsContent = new ArrayList<>();
        for (Entry<DxccEntity, Integer> entry : counting.entrySet()) {
            listStringsContent.add(new String[] { entry.getKey().getEntityCode().toString(), entry.getKey().getEntity(),
                    entry.getValue() + "" });
        }

        return CsvUtil.createCsvByteArray(header, listStringsContent);

    }

    @Override
    public byte[] getCalledByEntity(int editionId) throws FmreContestException {
        List<ContestQso> qsos = qsosService.getValidQsosOfEdition(editionId);

        Map<DxccEntity, Integer> counting = new HashMap<>();

        for (ContestQso qso : qsos) {
            DxccEntity dxccEntity = qso.getDxccEntity();
            if (counting.containsKey(dxccEntity)) {
                counting.put(dxccEntity, counting.get(dxccEntity) + 1);
            } else {
                counting.put(dxccEntity, 1);
            }
        }

        String[] header = { "DXCC ENTIY", "DXCC ENTITY NAME", "COUNT" };
        List<String[]> listStringsContent = new ArrayList<>();
        for (Entry<DxccEntity, Integer> entry : counting.entrySet()) {
            listStringsContent.add(new String[] { entry.getKey().getEntityCode().toString(), entry.getKey().getEntity(),
                    entry.getValue() + "" });
        }

        return CsvUtil.createCsvByteArray(header, listStringsContent);
    }

    @Override
    public byte[] getEmailsWithErrors(int editionId) throws FmreContestException {
        List<Email> emailsWithErrors = emailRepository.getEmailsWithErroresByEditionId(editionId);

        String[] header = { "EMAIL ID", "EMAIL SUBJECT", "ERRORS", "NUM QSOS", "ATTACHED FILES", "OK EMAILS" };
        List<String[]> listStringsContent = new ArrayList<>();
        for (Email email : emailsWithErrors) {
            String complexSubject = email.getSubject();
            String[] arraySubject = complexSubject.split("\\s|\\.|\\-");
            StringBuilder emailWithoutErrosIdsSB = new StringBuilder();
            List<String> excludedWords = Arrays.asList(new String[] { "", "txt", "cbr", "log" });
            for (String subjectElement : arraySubject) {
                if (subjectElement != null && excludedWords.contains(subjectElement.toLowerCase())) {
                    continue;
                }
                List<LastEmail> lastEmailList = lastEmailRepository.findByEmailSubjectAndEditionId(subjectElement,
                        email.getEdition().getId());
                if (lastEmailList != null && lastEmailList.size() > 0) {
                    for (LastEmail lastEmail : lastEmailList) {
                        emailWithoutErrosIdsSB.append(lastEmail.getEmailId()).append(", ");
                    }
                }
            }
            String emailWithoutErrosIds = emailWithoutErrosIdsSB.toString();
            emailWithoutErrosIds = (emailWithoutErrosIds != null && !"".equals(emailWithoutErrosIds)
                    && emailWithoutErrosIds.length() > 2)
                            ? emailWithoutErrosIds.substring(0, emailWithoutErrosIds.length() - 2)
                            : "";
            
            StringBuilder errorsStringBuilder = new StringBuilder();
            List<CatEmailError> errors = catEmailErrorRepository.getErrorsOfEmail(email);
            for (CatEmailError error : errors) {
                errorsStringBuilder.append(error.getDescripcion()).append(", ");
            }
            String errorsStr = errorsStringBuilder.toString();
            errorsStr = (errorsStr != null && !"".equals(errorsStr) && errorsStr.length() > 2)
                    ? errorsStr.substring(0, errorsStr.length() - 2)
                    : "";
                    
            List<AttachedFile> attachedFiles = email.getAttachedFiles();
            StringBuilder attachedFilesStringBuilder = new StringBuilder();
            for (AttachedFile attachedFile : attachedFiles) {
                attachedFilesStringBuilder.append(attachedFile.getFilename()).append(", ");
            }
            String attachedFilesStr = attachedFilesStringBuilder.toString();
            attachedFilesStr = (attachedFilesStr != null && !"".equals(attachedFilesStr)
                    && attachedFilesStr.length() > 2) ? attachedFilesStr.substring(0, attachedFilesStr.length() - 2)
                            : "";
                    
            List<ContestQso> qsos = contestQsoRepository.findByEmail(email);
            String numQsos = qsos != null ? qsos.size() + "" : ""; 
                    
            listStringsContent.add(new String[] { email.getId().toString(), email.getSubject(), errorsStr, numQsos,
                    attachedFilesStr, emailWithoutErrosIds });

        }

        return CsvUtil.createCsvByteArray(header, listStringsContent);
    }
}
