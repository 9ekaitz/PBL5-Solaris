package eus.solaris.solaris.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "solar_panel_model")
@Getter @Setter
@Generated
public class SolarPanelModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "i18n", nullable = false)
    private String i18n;

    @Column(name = "power", nullable = false)
    private Double power;

    @Column(name = "voltage", nullable = false)
    private Double voltage;

    @Column(name = "width", nullable = false)
    private Double width;

    @Column(name = "height", nullable = false)
    private Double height;

    @Column(name = "price", nullable = false)
    private Double price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "model")
    private Set<SolarPanel> solarPanel;

    @Version
    private Integer version;
}
