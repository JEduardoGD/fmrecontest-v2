package mx.fmre.rttycontest.persistence.geo.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Location {
    private String gridLocator;
    private BigDecimal lat;
    private BigDecimal lon;
    private String googleMapsLink;
    private String region;
    private String error;
}
