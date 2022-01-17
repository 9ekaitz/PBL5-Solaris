package eus.solaris.solaris.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "privilege")
@Getter @Setter
@Generated
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 64)
    private String code;

    @Column(name = "i18n", nullable = false, length = 64)
    private String i18n;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;

    @Version
    @lombok.EqualsAndHashCode.Exclude
    private Integer version;

}
