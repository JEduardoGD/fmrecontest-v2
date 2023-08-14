package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "REL_EXTERNAL_LOG_EDITION")
public class RelExternallogEdition implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3954849712523996652L;

    @EmbeddedId
    private RelExternallogEditionPk relExternallogEditionPk;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_CONTEST_LOG", insertable = false, updatable = false)
    private ContestLog contestLog;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="N_ID_EDITION", insertable = false, updatable = false)
    private Edition edition;

    @Column(name = "b_isactive")
    private Boolean active;

    @Column(name = "d_datetime")
    private Date datetime;

}
