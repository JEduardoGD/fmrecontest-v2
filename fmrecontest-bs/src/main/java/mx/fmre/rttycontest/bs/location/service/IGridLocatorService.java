package mx.fmre.rttycontest.bs.location.service;

import mx.fmre.rttycontest.bs.location.exception.GridLocatorException;
import mx.fmre.rttycontest.persistence.geo.dto.Location;

public interface IGridLocatorService {

    Location getLocationOf(String location) throws GridLocatorException;

}
