package mx.fmre.rttycontest.api.service;

import java.util.List;

import mx.fmre.rttycontest.api.dto.BandDto;
import mx.fmre.rttycontest.exception.FmreContestException;

public interface IBandService {
	public List<BandDto> getAll() throws FmreContestException;
}
