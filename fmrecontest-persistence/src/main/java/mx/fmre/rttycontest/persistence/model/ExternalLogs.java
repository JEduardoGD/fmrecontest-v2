package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_log")
public class ExternalLogs implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2634751748102336256L;

    @EmbeddedId
    private ExternalLogsId externalLogsId;

    @Column(name = "id")
    private Long id;
    @Column(name = "f_email")
    private Long email;
    @Column(name = "version_number")
    private Long versionNumber;
    @Column(name = "category_assisted")
    private String categoryAssisted;
    @Column(name = "category_band")
    private String categoryBand;
    @Column(name = "category_mode")
    private String categoryMode;
    @Column(name = "category_operator")
    private String categoryOperator;
    @Column(name = "category_power")
    private String categoryPower;
    @Column(name = "category_station")
    private String categoryStation;
    @Column(name = "category_time")
    private String categoryTime;
    @Column(name = "category_transmitter")
    private String categoryTransmitter;
    @Column(name = "category_overlay")
    private String categoryOverlay;
    @Column(name = "claimed_score")
    private Long claimedScore;
    @Column(name = "club")
    private String club;
    @Column(name = "contest")
    private String contest;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "location")
    private String location;
    @Column(name = "name")
    private String name;
    @Column(name = "address1")
    private String address1;
    @Column(name = "address2")
    private String address2;
    @Column(name = "address3")
    private String address3;
    @Column(name = "address4")
    private String address4;
    @Column(name = "operators")
    private String operators;
    @Column(name = "offline")
    private String offline;
    @Column(name = "soapbox")
    private String soapbox;
    
}
