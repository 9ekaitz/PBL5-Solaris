package eus.solaris.solaris.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "image")
    private String image;

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
    
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    
    @Version
    private Integer version;
}
