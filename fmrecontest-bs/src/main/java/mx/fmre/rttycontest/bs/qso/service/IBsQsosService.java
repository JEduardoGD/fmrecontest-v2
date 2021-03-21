package mx.fmre.rttycontest.bs.qso.service;

import java.util.List;

import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.ContestQso;

public interface IBsQsosService {

    List<ContestQso> getValidQsosOfEdition(int editionId) throws FmreContestException;

}
