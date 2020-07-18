package mx.fmre.rttycontest.api.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import mx.fmre.rttycontest.api.dto.QsoDto;
import mx.fmre.rttycontest.persistence.model.CatBand;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.RelQsoConteo;

public abstract class QsoUtil {
	
	private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static QsoDto map(DxccEntity dxccEntity, RelQsoConteo relQsoConteo, CatBand qsoBand, ContestQso x) {
		String rowClass = null;
		rowClass = (qsoBand == null ||  dxccEntity == null) ? "table-danger" : null;
		if(x.getError() != null && x.getError().booleanValue() == true)
			rowClass = "table-warning";
		
		QsoDto qsoDto = new QsoDto();
		qsoDto.setConteoId(relQsoConteo.getConteo().getId());
		qsoDto.setId(x.getId());
		qsoDto.setFrequency(x.getFrequency());
		qsoDto.setCallsignE(x.getCallsigne());
		qsoDto.setCallsignR(x.getCallsignr());
		qsoDto.setDateTime(df.format(x.getDatetime()));
		qsoDto.setExchangeE(x.getExchangee());
		qsoDto.setExchangeR(x.getExchanger());
		qsoDto.setRstE(x.getRste());
		qsoDto.setRstR(x.getRstr());
		qsoDto.setDxccEntityId(dxccEntity != null ? dxccEntity.getId() : null);
		qsoDto.setDxccEntity(dxccEntity != null ? dxccEntity.getEntity() : null);
		qsoDto.setPoints(relQsoConteo.getPoints());
		qsoDto.setMultiply(relQsoConteo.isMultiply());
		qsoDto.setQsoBand(qsoBand != null ? qsoBand.getBand() : "NOT FOUND");
		qsoDto.setQsoBandId(qsoBand != null ? qsoBand.getId() : null);
		qsoDto.setDxccOrBandError(x.getError() == null || x.getError().booleanValue() == false ? false : true);
		qsoDto.setRowClass(rowClass);
		return qsoDto;
	}
}
