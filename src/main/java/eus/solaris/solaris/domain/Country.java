package eus.solaris.solaris.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "country")
@Getter @Setter
public class Country implements Serializable{

    private static final long serialVersionUID = -7312439926852854962L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 64)
    private String code;

    @Column(name = "i18n", nullable = false, length = 64)
    private String i18n;
    
}
