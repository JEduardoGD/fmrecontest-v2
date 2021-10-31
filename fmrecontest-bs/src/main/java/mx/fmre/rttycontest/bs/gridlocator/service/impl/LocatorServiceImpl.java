package mx.fmre.rttycontest.bs.gridlocator.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.gridlocator.service.ILocatorService;
import mx.fmre.rttycontest.bs.location.exception.GridLocatorException;
import mx.fmre.rttycontest.bs.location.service.IGridLocatorService;
import mx.fmre.rttycontest.persistence.geo.dto.Location;
import mx.fmre.rttycontest.persistence.model.CatGridlocatorState;
import mx.fmre.rttycontest.persistence.repository.ICatGridlocatorStateRepository;

@Slf4j
@Service
public class LocatorServiceImpl implements ILocatorService {
    @Autowired ICatGridlocatorStateRepository catGridlocatorStateRepository;
    @Autowired IGridLocatorService            gridLocatorService;

    @Override
    public CatGridlocatorState getGridLocatorstateOfGridlocator(String gridlocator) throws LocatorServiceException {
        if (null == gridlocator || "".equals(gridlocator)) {
            return null;
        }
        CatGridlocatorState catGridlocatorState = catGridlocatorStateRepository.findByGridlocator(gridlocator);
        if (null != catGridlocatorState) {
            return catGridlocatorState;
        }

        try {
            Location res = gridLocatorService.getLocationOf(gridlocator);
            catGridlocatorState = new CatGridlocatorState(null, res.getGridLocator(), res.getRegion(), new Date());
            if (null == catGridlocatorState || catGridlocatorState.getState() == null) {
                return null;
            }
            catGridlocatorState = catGridlocatorStateRepository.save(catGridlocatorState);
            return catGridlocatorState;
        } catch (GridLocatorException e) {
            log.error(e.getLocalizedMessage());
            throw new LocatorServiceException(e);
        }
    }
}
