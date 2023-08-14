package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class RelExternallogEditionPk implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4064391166022833870L;
    @Column(name = "ID_CONTEST_LOG", insertable=false, updatable=false)
    private Long contestLog;
    
    @Column(name = "N_ID_EDITION", insertable=false, updatable=false)
    private Integer edition;

    public RelExternallogEditionPk() {
        super();
    }

    public RelExternallogEditionPk(Long contestLog, Integer edition) {
        super();
        this.contestLog = contestLog;
        this.edition = edition;
    }

    public RelExternallogEditionPk(ContestLog contestLog, Edition edition) {
        super();
        this.contestLog = contestLog.getId();
        this.edition = edition.getId();
    }

}
