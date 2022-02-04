package mx.fmre.rttycontest.evaluate.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.evaluate.services.IGeneralServiceUtils;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;

@Service
public class GeneralServiceUtilsImpl implements IGeneralServiceUtils {
    @Autowired
    IEmailRepository emailRepository;

    @Override
    public List<Email> filter(List<Email> listEmail, Edition edition) {
        List<Email> finalListEmail = new ArrayList<Email>();

        List<String> listCallsign = new ArrayList<>();
        for (Email email : listEmail) {
            if (null == email.getContestLog()) {
                continue;
            }
            String callsign = email.getContestLog().getCallsign();
            if (null == callsign) {
                continue;
            }
            if (!listCallsign.contains(callsign.toUpperCase()) && !listCallsign.contains(callsign.toLowerCase())) {
                listCallsign.add(callsign);
            }
        }

        for (String callsign : listCallsign) {
            List<Email> lastEmailsOfCallsign = getLastEmailsOfCallsign(callsign, edition);
            if (null != lastEmailsOfCallsign) {
                finalListEmail.addAll(lastEmailsOfCallsign);
            }
        }
        return finalListEmail;
    }

    private List<Email> getLastEmailsOfCallsign(String callsign, Edition edition) {
        if (callsign == null || edition == null) {
            return null;
        }
        if ("".equals(callsign)) {
            return null;
        }
        Integer maxIdEmail = emailRepository.getMaxByCallsignAndEdition(callsign, edition.getId());
        if (maxIdEmail == null) {
            return null;
        }
        Email email = emailRepository.findById(maxIdEmail).orElse(null);
        if (callsign.toLowerCase().endsWith("/m")) {
            ContestLog contestLog = email.getContestLog();
            Long contestLogGrup = contestLog.getGroup();
            return emailRepository.getAllByContestogGroup(contestLogGrup);

        }
        return Arrays.asList(email);
    }
}
