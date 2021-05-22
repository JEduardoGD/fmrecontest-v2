package mx.fmre.rttycontest.bs.location.service.impl;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.location.exception.GridLocatorException;
import mx.fmre.rttycontest.bs.location.service.IGridLocatorService;
import mx.fmre.rttycontest.persistence.geo.dto.Location;

@Service
public class GridLocatorServiceImpl implements IGridLocatorService {

    @Value("${service.region.url}")
    private String serviceRegionUrl;

    @Override
    public Location getLocationOf(String location) throws GridLocatorException {
        String url = String.format("%s/%s", serviceRegionUrl, location);

        ClientBuilder cb = ClientBuilder.newBuilder();
        cb.connectTimeout(3, TimeUnit.SECONDS);
        cb.readTimeout(3, TimeUnit.SECONDS);
        cb.register(new JacksonFeature());
        Client client = cb.build();
        try {
            return client.target(url).request(MediaType.APPLICATION_JSON).get(Location.class);
        } catch (Exception e) {
            throw new GridLocatorException(e);
        }
    }
}
