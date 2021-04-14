package mx.fmre.rttycontest.persistence.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CAT_STATE")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "N_ID_STATE")
    private Long id;

    @Column(name = "S_SIGLAS")
    private String siglas;

    @Column(name = "S_STATE")
    private String nombre;

    // bi-directional many-to-one association to Contestqso
    @OneToMany(mappedBy = "originState", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ContestQso> contestQsosOrigin;

    // bi-directional many-to-one association to Contestqso
    @OneToMany(mappedBy = "destState", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ContestQso> contestQsosDest;
}
