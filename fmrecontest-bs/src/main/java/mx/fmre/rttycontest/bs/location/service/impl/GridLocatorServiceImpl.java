package mx.fmre.rttycontest.bs.location.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.fmre.rttycontest.bs.location.service.IGridLocatorService;
import mx.fmre.rttycontest.persistence.geo.dto.Location;

@Service
public class GridLocatorServiceImpl implements IGridLocatorService {
    
    @Override
    public Location getLocationOf(String location) {
        String url = String.format("http://r.serpiente.digital:8080/getregionof/%s", location);
        ResponseEntity<Location> restTemplate = new RestTemplate().getForEntity(url , Location.class);
        return restTemplate.getBody();
    }
}
