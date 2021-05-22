package mx.fmre.rttycontest.recibir.business.base;

import java.util.List;

import mx.fmre.rttycontest.persistence.model.AttachedFile;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;

public class BaseVerificationsService {


    protected boolean verify_EMAIL_WITHOUT_SUBJECT(Email email, Edition edition, List<AttachedFile> attachedFiles) {
        return (email.getSubject() == null || "".equals(email.getSubject()));
    }

    protected boolean verify_EMAIL_WITHOUT_ATTACHED_FILES(Email email, Edition edition,
            List<AttachedFile> attachedFiles) {
        return (attachedFiles == null || attachedFiles.size() <= 0);
    }

    protected boolean verify_SUBJECT_WITH_MORE_THAN_ONE_WORD(Email email, Edition edition,
            List<AttachedFile> attachedFiles) {
        String subject = email.getSubject();
        if(subject != null && !"".equals(subject)) {
            String[] arr = subject.split("\\s");
            return arr.length > 1;
        }
        return false;
    }

    protected boolean verify_SUBJECT_NOT_EQUALS_CONTESTLOG_CALLSIGN(Email email, Edition edition, List<AttachedFile> attachedFiles, IContestLogRepository contestLogRepository) {
        ContestLog contestLog = contestLogRepository.findByEmail(email);
        if(contestLog == null || contestLog.getCallsign() == null || email.getSubject() == null)
            return false;
        String contestLogCallsign = contestLog.getCallsign().toUpperCase();
        String subject = email.getSubject().toUpperCase();
        return !contestLogCallsign.equals(subject);
    }

    protected boolean verify_EMAIL_WITHOUT_CONTSTLOG(Email email, Edition edition, List<AttachedFile> attachedFiles, IContestLogRepository contestLogRepository) {
        ContestLog contestLog = contestLogRepository.findByEmail(email);
        if(contestLog == null)
            return true;
        return false;
    }
}
