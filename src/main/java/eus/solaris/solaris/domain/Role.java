package eus.solaris.solaris.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter @Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
    
    @OneToMany(mappedBy = "role")
    private Set<User> users;

    @Column(name = "i18n", nullable = false, length = 64)
    private String i18n;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Privilege> privileges;
    
    @Version
    private Integer version;

}
