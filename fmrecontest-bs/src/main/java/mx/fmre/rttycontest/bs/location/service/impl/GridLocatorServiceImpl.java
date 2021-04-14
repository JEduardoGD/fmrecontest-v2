package mx.fmre.rttycontest.bs.location.service.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.location.service.IGridLocatorService;
import mx.fmre.rttycontest.persistence.geo.dto.Location;

@Service
public class GridLocatorServiceImpl implements IGridLocatorService {
    
    @Value("${service.region.url}")
    private String serviceRegionUrl;

    @Override
    public Location getLocationOf(String location) {
        String url = String.format("%s/%s", serviceRegionUrl, location);
        Client client = ClientBuilder.newClient().register(new JacksonFeature());
        return client.target(url).request(MediaType.APPLICATION_JSON).get(new GenericType<Location>() {
        });
    }
}
