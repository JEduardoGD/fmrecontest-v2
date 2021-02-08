package mx.fmre.rttycontest.bs.externaldxccservice;

import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dxcc.dao.CallsignDAO;
import mx.fmre.rttycontest.bs.dxcc.service.IDxccService;
import mx.fmre.rttycontest.bs.util.QrzUtil;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;

@Slf4j
public class ExternalDxccServiceImpl {
	
	private static final String QRZ_ORIGEN = "QRZ.com";
	private static final String PUEBLA_DX_ORIGEN = "Puebla DX";
	
	public static DxccEntity getDxccFromExternalServicesByCallsign(ApplicationContext appContext, IDxccEntityRepository dxccEntityRepository, String callsign) throws FmreContestException {
		DxccEntity dxccEntity;
		
		// 3rd attempt, from QRZ
		IDxccService dxccServiceQrz = appContext.getBean("qrzDxccServiceQrzImpl", IDxccService.class);
		CallsignDAO resQrz = dxccServiceQrz.getDxccFromCallsign(callsign);
		if (resQrz != null) {
			Long dxccEntityNumber = resQrz.getDxcc();
			dxccEntity = dxccEntityRepository.findById(dxccEntityNumber).orElse(null);
			if (dxccEntity != null) {
				log.info("{} from qrz", callsign);
				return dxccEntity;
			}
			if (dxccEntity == null && resQrz != null) {
				dxccEntity = QrzUtil.parse(resQrz);
				dxccEntity.setOrigen(QRZ_ORIGEN);
				dxccEntity = dxccEntityRepository.save(dxccEntity);
				return dxccEntity;
			}
		}
		
		// 4rd attempt, from Puebla DX
		IDxccService dxccServicePueblaDx = appContext.getBean("dxccServicePueblaDx", IDxccService.class);
		CallsignDAO resPueblaDx = dxccServicePueblaDx.getDxccFromCallsign(callsign);
		if (resPueblaDx != null) {
			Long dxccEntityNumber = resPueblaDx.getDxcc();
			dxccEntity = dxccEntityRepository.findById(dxccEntityNumber).orElse(null);
			if (dxccEntity != null) {
				log.info("{} from Puebla DX", callsign);
				return dxccEntity;
			}
			if (dxccEntity == null && resPueblaDx != null) {
				log.info("{} from Puebla DX", callsign);
				dxccEntity = QrzUtil.parse(resPueblaDx);
				dxccEntity.setOrigen(PUEBLA_DX_ORIGEN);
				dxccEntity = dxccEntityRepository.save(dxccEntity);
				return dxccEntity;
			}
		}
		
		// NOT FOUND
		return null;
	}
}
