package mx.fmre.rttycontest.bs.dxcc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dxcc.service.ExternalDxccService;
import mx.fmre.rttycontest.bs.dxcc.service.IDxccService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;

@Slf4j
@Service
public class ExternalDxccServiceImpl implements ExternalDxccService {
    
    @Autowired private IDxccEntityRepository dxccEntityRepository;
    @Autowired private IDxccService qrzDxccServiceQrzImpl;
    @Autowired private IDxccService dxccServicePueblaDx;

    private static final String QRZ_ORIGEN = "QRZ.com";
    private static final String PUEBLA_DX_ORIGEN = "Puebla DX";

    @Override
    public DxccEntity getDxccFromExternalServicesByCallsign(String callsign) throws FmreContestException {
        // 2rd attempt, from QRZ
        DxccEntity resQrz = qrzDxccServiceQrzImpl.getDxccFromCallsign(callsign);
        if (resQrz != null) {
            log.info("{} from qrz", callsign);
            DxccEntity persistedEntity = dxccEntityRepository.findByDxccEntityCodeBeforeYear(resQrz.getEntityCode());
            if (persistedEntity != null) {
                return persistedEntity;
            }
            resQrz.setOrigen(QRZ_ORIGEN);
            DxccEntity savvedDxccEntity = dxccEntityRepository.save(resQrz);
            return savvedDxccEntity;
        }

        // 3rd attempt, from Puebla DX
        DxccEntity resPueblaDx = dxccServicePueblaDx.getDxccFromCallsign(callsign);
        if (resPueblaDx != null && resPueblaDx.getEntityCode() != null) {
            log.info("{} from qrz", callsign);
            DxccEntity persistedEntity = dxccEntityRepository
                    .findByDxccEntityCodeBeforeYear(resPueblaDx.getEntityCode());
            if (persistedEntity != null) {
                return persistedEntity;
            }
            resPueblaDx.setOrigen(PUEBLA_DX_ORIGEN);
            dxccEntityRepository.save(resQrz);
            DxccEntity savvedDxccEntity = dxccEntityRepository.findByDxccEntityNameBeforeYear(callsign);
            return savvedDxccEntity;
        }

        // NOT FOUND
        return null;
    }

    @Override
    public DxccEntity getDxccFromExternalServicesByEntityName(String enntityName) throws FmreContestException {
        DxccEntity dxccEntity;
        
        dxccEntity = dxccEntityRepository.findByDxccEntityNameBeforeYear(enntityName);

        return dxccEntity;
    }
}
