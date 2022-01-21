package eus.solaris.solaris.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

@Entity
@Table(name = "product")
@Data
@Generated
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    private Size size;

    @ManyToOne(fetch = FetchType.LAZY)
    private SolarPanelModel model;

    @Column(nullable = false)
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<ProductDescription> descriptions;

    @Column(nullable = false)
    private String imagePath;

    @Version
    @EqualsAndHashCode.Exclude
    private Integer version;
}