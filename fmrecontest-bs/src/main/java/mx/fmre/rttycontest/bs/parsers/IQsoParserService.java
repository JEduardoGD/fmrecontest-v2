package mx.fmre.rttycontest.bs.parsers;

import java.util.List;

import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Edition;

public interface IQsoParserService {

    ContestLog parse(Edition edition, List<String> stringList) throws FmreContestException;
}
