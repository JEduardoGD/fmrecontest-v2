package mx.fmre.rttycontest.bs.gridlocator.service;

import mx.fmre.rttycontest.bs.gridlocator.service.impl.LocatorServiceException;
import mx.fmre.rttycontest.persistence.model.CatGridlocatorState;

public interface ILocatorService {

    CatGridlocatorState getGridLocatorstateOfGridlocator(String gridlocator) throws LocatorServiceException;

}
