package eus.solaris.solaris.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.ToString;

@Entity
@Table(name = "solar_panel_model")
@Data
@Generated
public class SolarPanelModel implements Serializable {

    private static final long serialVersionUID = 3019735005226998956L;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    private Size size;

    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    private Color color;

    @Version
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Integer version;
}
