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

import lombok.EqualsAndHashCode;
import lombok.Generated;

@Entity
@Table(name = "product")
@Generated
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(nullable = false, name = "imagePath")
    private String imagePath;

    @Column(name = "material")
    private String material;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "color")
    private String color;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private SolarPanelModel solarPanelModel;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<ProductDescription> descriptions;

    @Version
    @EqualsAndHashCode.Exclude
    private Integer version;
}