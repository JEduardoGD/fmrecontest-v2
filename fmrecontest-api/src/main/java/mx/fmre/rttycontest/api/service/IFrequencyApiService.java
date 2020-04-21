package mx.fmre.rttycontest.api.service;

import java.util.List;

import mx.fmre.rttycontest.api.dto.FrequencyDTO;
import mx.fmre.rttycontest.exception.FmreContestException;

public interface IFrequencyApiService {
	public List<FrequencyDTO> getAll() throws FmreContestException;
}
