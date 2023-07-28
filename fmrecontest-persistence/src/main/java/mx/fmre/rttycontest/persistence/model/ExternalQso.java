package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_qsos")
public class ExternalQso implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 8173608221745756274L;

    @EmbeddedId
    private ExternalQsoKey externalQsoKey;

    @Column(name = "Freq")
    private String freq;
    @Column(name = "Callsign2")
    private String callsign2;
    @Column(name = "RST2")
    private String rst2;
    @Column(name = "Intercambio2")
    private String intercambio2;
    @Column(name = "DXCC1")
    private String dxcc1;
    @Column(name = "DXCC2")
    private String dxcc2;
    @Column(name = "matching")
    private Long matching;
    @Column(name = "error")
    private String error;
    @Column(name = "crono")
    private BigInteger crono;
    @Column(name = "puntos")
    private Long puntos;
    @Column(name = "multiplicador")
    private Long multiplicador;

}
