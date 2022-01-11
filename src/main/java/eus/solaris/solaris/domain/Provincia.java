package eus.solaris.solaris.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "relacion_comunidad_provincia")
@Getter
@Setter
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_provincia")
    private Integer id;

    @Column(name = "capital_provincia", nullable = false)
    private String capital_provincia;

    @ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_comunidad", nullable = false)
    private Comunidad comunidad;
}
