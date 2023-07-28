package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class ExternalQsoKey implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3269138144128775240L;
    @Column(name = "Fecha")
    private Date fecha;
    @Column(name = "Banda")
    private String banda;
    @Column(name = "Modo")
    private String modo;
    @Column(name = "Callsign1")
    private String callsing1;
    @Column(name = "Intercambio1")
    private String interbambio1;

    public ExternalQsoKey() {
        super();
    }

    public ExternalQsoKey(Date fecha, String banda, String modo, String callsing1, String interbambio1) {
        super();
        this.fecha = fecha;
        this.banda = banda;
        this.modo = modo;
        this.callsing1 = callsing1;
        this.interbambio1 = interbambio1;
    }
}
