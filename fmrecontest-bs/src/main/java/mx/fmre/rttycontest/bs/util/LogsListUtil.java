package mx.fmre.rttycontest.bs.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.RelExternallogEdition;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IRelExternallogEditionRepository;

public abstract class LogsListUtil {

    protected List<ContestLog> getContexLogToEvaluate(IRelExternallogEditionRepository relExternallogEditionRepository,
            IContestLogRepository contestLogRepository, List<Email> emailsFiltered, Edition edition) {
        List<ContestLog> contestLogToEvaluate = new ArrayList<>();

        for (Email emailFiltered : emailsFiltered) {
            ContestLog contestLog = emailFiltered.getContestLog();
            contestLogToEvaluate.add(contestLog);
        }

        List<RelExternallogEdition> relExternallogEditionList = relExternallogEditionRepository.findAll().stream()
                .filter(r -> r.getEdition().getId().equals(edition.getId())).collect(Collectors.toList());

        for (RelExternallogEdition relExternallogEdition : relExternallogEditionList) {
            contestLogToEvaluate
                    .add(contestLogRepository.findById(relExternallogEdition.getContestLog().getId()).orElse(null));
        }

        return contestLogToEvaluate;

    }
}
