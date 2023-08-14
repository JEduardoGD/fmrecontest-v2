package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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


    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "f_email")
    private Long email;
    @Column(name = "callsign")
    private String callsign;
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
    @Column(name = "ano")
    private String ano;
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ExternalLogs other = (ExternalLogs) obj;
        if (address1 == null) {
            if (other.address1 != null)
                return false;
        } else if (!address1.equals(other.address1))
            return false;
        if (address2 == null) {
            if (other.address2 != null)
                return false;
        } else if (!address2.equals(other.address2))
            return false;
        if (address3 == null) {
            if (other.address3 != null)
                return false;
        } else if (!address3.equals(other.address3))
            return false;
        if (address4 == null) {
            if (other.address4 != null)
                return false;
        } else if (!address4.equals(other.address4))
            return false;
        if (ano == null) {
            if (other.ano != null)
                return false;
        } else if (!ano.equals(other.ano))
            return false;
        if (callsign == null) {
            if (other.callsign != null)
                return false;
        } else if (!callsign.equals(other.callsign))
            return false;
        if (categoryAssisted == null) {
            if (other.categoryAssisted != null)
                return false;
        } else if (!categoryAssisted.equals(other.categoryAssisted))
            return false;
        if (categoryBand == null) {
            if (other.categoryBand != null)
                return false;
        } else if (!categoryBand.equals(other.categoryBand))
            return false;
        if (categoryMode == null) {
            if (other.categoryMode != null)
                return false;
        } else if (!categoryMode.equals(other.categoryMode))
            return false;
        if (categoryOperator == null) {
            if (other.categoryOperator != null)
                return false;
        } else if (!categoryOperator.equals(other.categoryOperator))
            return false;
        if (categoryOverlay == null) {
            if (other.categoryOverlay != null)
                return false;
        } else if (!categoryOverlay.equals(other.categoryOverlay))
            return false;
        if (categoryPower == null) {
            if (other.categoryPower != null)
                return false;
        } else if (!categoryPower.equals(other.categoryPower))
            return false;
        if (categoryStation == null) {
            if (other.categoryStation != null)
                return false;
        } else if (!categoryStation.equals(other.categoryStation))
            return false;
        if (categoryTime == null) {
            if (other.categoryTime != null)
                return false;
        } else if (!categoryTime.equals(other.categoryTime))
            return false;
        if (categoryTransmitter == null) {
            if (other.categoryTransmitter != null)
                return false;
        } else if (!categoryTransmitter.equals(other.categoryTransmitter))
            return false;
        if (claimedScore == null) {
            if (other.claimedScore != null)
                return false;
        } else if (!claimedScore.equals(other.claimedScore))
            return false;
        if (club == null) {
            if (other.club != null)
                return false;
        } else if (!club.equals(other.club))
            return false;
        if (contest == null) {
            if (other.contest != null)
                return false;
        } else if (!contest.equals(other.contest))
            return false;
        if (createdBy == null) {
            if (other.createdBy != null)
                return false;
        } else if (!createdBy.equals(other.createdBy))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (emailAddress == null) {
            if (other.emailAddress != null)
                return false;
        } else if (!emailAddress.equals(other.emailAddress))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (offline == null) {
            if (other.offline != null)
                return false;
        } else if (!offline.equals(other.offline))
            return false;
        if (operators == null) {
            if (other.operators != null)
                return false;
        } else if (!operators.equals(other.operators))
            return false;
        if (soapbox == null) {
            if (other.soapbox != null)
                return false;
        } else if (!soapbox.equals(other.soapbox))
            return false;
        if (versionNumber == null) {
            if (other.versionNumber != null)
                return false;
        } else if (!versionNumber.equals(other.versionNumber))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address1 == null) ? 0 : address1.hashCode());
        result = prime * result + ((address2 == null) ? 0 : address2.hashCode());
        result = prime * result + ((address3 == null) ? 0 : address3.hashCode());
        result = prime * result + ((address4 == null) ? 0 : address4.hashCode());
        result = prime * result + ((ano == null) ? 0 : ano.hashCode());
        result = prime * result + ((callsign == null) ? 0 : callsign.hashCode());
        result = prime * result + ((categoryAssisted == null) ? 0 : categoryAssisted.hashCode());
        result = prime * result + ((categoryBand == null) ? 0 : categoryBand.hashCode());
        result = prime * result + ((categoryMode == null) ? 0 : categoryMode.hashCode());
        result = prime * result + ((categoryOperator == null) ? 0 : categoryOperator.hashCode());
        result = prime * result + ((categoryOverlay == null) ? 0 : categoryOverlay.hashCode());
        result = prime * result + ((categoryPower == null) ? 0 : categoryPower.hashCode());
        result = prime * result + ((categoryStation == null) ? 0 : categoryStation.hashCode());
        result = prime * result + ((categoryTime == null) ? 0 : categoryTime.hashCode());
        result = prime * result + ((categoryTransmitter == null) ? 0 : categoryTransmitter.hashCode());
        result = prime * result + ((claimedScore == null) ? 0 : claimedScore.hashCode());
        result = prime * result + ((club == null) ? 0 : club.hashCode());
        result = prime * result + ((contest == null) ? 0 : contest.hashCode());
        result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((offline == null) ? 0 : offline.hashCode());
        result = prime * result + ((operators == null) ? 0 : operators.hashCode());
        result = prime * result + ((soapbox == null) ? 0 : soapbox.hashCode());
        result = prime * result + ((versionNumber == null) ? 0 : versionNumber.hashCode());
        return result;
    }
    
    
}
