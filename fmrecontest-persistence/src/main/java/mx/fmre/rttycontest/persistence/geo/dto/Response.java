package mx.fmre.rttycontest.persistence.geo.dto;

import lombok.Data;

@Data
public class Response {
    private Location locationA;
    private Location locationB;
    private String distance;
}
