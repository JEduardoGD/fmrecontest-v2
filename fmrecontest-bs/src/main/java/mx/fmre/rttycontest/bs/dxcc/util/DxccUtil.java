package mx.fmre.rttycontest.bs.dxcc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dxcc.service.ExternalDxccService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.dxcc.dao.DxccEntityCallsignDAO;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;

@Slf4j
public class DxccUtil {

    
    public static Map<String, DxccEntity> fillDxccMap(IDxccEntityRepository dxccEntityRepository, Edition edition) {
        Map<String, DxccEntity> map = new HashMap<>();
        List<DxccEntityCallsignDAO> listObjectsQsos = dxccEntityRepository.getAllByEditionOnQso(edition);
        listObjectsQsos.stream().forEach(o -> map.put(o.getCallsign(), o.getDxccEntity()));
        List<DxccEntityCallsignDAO> listObjectsLogs = dxccEntityRepository.getAllByEditionOnLogs(edition);
        listObjectsLogs.stream().forEach(o -> map.put(o.getCallsign(), o.getDxccEntity()));
        return map;
    }

    public static DxccEntity getDxccOf(ExternalDxccService externalDxccService, Map<String, DxccEntity> map, String callsign, Edition edition) throws FmreContestException {
        DxccEntity dxccEntity;
        
        // 1st atempt, from de map.
        dxccEntity = map.get(callsign);
        if(dxccEntity != null) {
            log.info("{} from map", callsign);
            return dxccEntity;
        }
        
        // 2nd and 3rd atempts, from external services
        dxccEntity = externalDxccService.getDxccFromExternalServicesByCallsign(callsign);
        
        if(dxccEntity != null) {
            log.debug("{} from external Services", callsign);
            map.put(callsign, dxccEntity);
            return dxccEntity;
        }
        
        return null;
    }
}
