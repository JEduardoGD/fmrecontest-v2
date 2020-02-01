package mx.fmre.rttycontest.bs.parsers;

import java.util.List;

import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.ContestLog;

public interface IQsoParserService {

	public ContestLog parse(List<String> stringList) throws FmreContestException;
}
