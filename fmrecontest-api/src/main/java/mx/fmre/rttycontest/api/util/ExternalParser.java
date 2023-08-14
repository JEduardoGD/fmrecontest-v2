package mx.fmre.rttycontest.api.util;

import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.ExternalLogs;
import mx.fmre.rttycontest.persistence.model.ExternalQso;
import mx.fmre.rttycontest.persistence.model.ExternalQsoKey;

public abstract class ExternalParser {
    public static ContestQso parse(ExternalQso externalQso) {
        ExternalQso e = externalQso;
        ExternalQsoKey k = e.getExternalQsoKey();

        ContestQso q = new ContestQso();

        q.setCallsigne(k.getCallsing1());
        q.setCallsignr(e.getCallsign2());
        q.setDatetime(k.getFecha());
        q.setExchangee(k.getInterbambio1());
        q.setExchanger(e.getIntercambio2());
        Integer freq = parseFrequency(externalQso);
        q.setFrequency(freq != null ? freq.intValue() : null);
        q.setMode(k.getModo());
        q.setRste(k.getInterbambio1());
        q.setRstr(e.getIntercambio2());
        

        return q;
    }

    private static Integer parseFrequency(ExternalQso externalQso) {
        if (externalQso == null || externalQso.getFreq() == null || "".equals(externalQso.getFreq())) {
            return null;
        }

        String externalQsoFreque = externalQso.getFreq();

        return Integer.valueOf(externalQsoFreque);
    }

    public static ContestLog parse(ExternalLogs externalLog) {
        ExternalLogs e = externalLog;
        ContestLog cl = new ContestLog();

        cl.setAddress(e.getAddress1());
        cl.setAddressCity(e.getAddress2());
        cl.setAddressCountry(e.getAddress3());
        cl.setAddressStateProvince(e.getAddress4());
        cl.setCallsign(e.getCallsign());
        cl.setCategoryBand(e.getCategoryBand());
        cl.setCategoryMode(e.getCategoryMode());
        cl.setCategoryOperator(e.getCategoryOperator());
        cl.setCategoryPower(e.getCategoryPower());
        Integer claimedScore = parseClaimedScore(e);
        cl.setClaimedScore(claimedScore != null ? claimedScore.intValue() : 0);
        cl.setClub(e.getClub());
        cl.setContest(e.getContest());
        cl.setCreateBy(e.getCreatedBy());
        cl.setEmailAddress(e.getEmailAddress());
        cl.setName(e.getName());
        cl.setOperators(e.getOperators());
        cl.setSoapbox(e.getSoapbox());
        cl.setChecklog(false);
        cl.setGroup(null);
        cl.setGridlocator(null);
        cl.setDxccEntity(null);
        cl.setDxccNotFound(null);

        return cl;
    }

    private static Integer parseClaimedScore(ExternalLogs externalLog) {
        if (externalLog == null || externalLog.getClaimedScore() == null) {
            return null;
        }
        Long externalClaimedScore = externalLog.getClaimedScore();
        return externalClaimedScore.intValue();
    }
}
