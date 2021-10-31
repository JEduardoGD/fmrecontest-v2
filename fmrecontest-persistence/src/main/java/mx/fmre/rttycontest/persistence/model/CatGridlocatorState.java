package mx.fmre.rttycontest.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CAT_GRIDLOCATOR_STATE")
public class CatGridlocatorState implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6232421938959547227L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "N_ID_GRIDLOCATOR_STATE")
    private Integer id;

    @Column(name = "GRIDLOCATOR")
    private String gridlocator;

    @Column(name = "STATE")
    private String state;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    public boolean isTheSame(CatGridlocatorState catGridlocatorState) {
        if (catGridlocatorState == null) {
            return false;
        }
        if (null == catGridlocatorState.getState()) {
            return false;
        }
        if (catGridlocatorState.getState().equals(this.getState())) {
            return true;
        }
        return false;
    }
}
