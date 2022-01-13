package eus.solaris.solaris.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`user`")
@Getter @Setter
public class User implements Serializable {
    
    private static final long serialVersionUID = -8446982019565427240L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "first_surname")
    private String firstSurname;
    
    @Column(name = "second_surname")
    private String secondSurname;
    
    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @OrderBy("default_address DESC")
    private List<Address> addresses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @OrderBy("default_method ASC")
    private List<PaymentMethod> paymentMethods;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<SolarPanel> solarPanels;
    
    @Version
    private Integer version;
    
}
