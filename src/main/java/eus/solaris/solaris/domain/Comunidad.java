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
@Table(name = "comunidades")
@Getter
@Setter
public class Comunidad implements Serializable {

    private static final long serialVersionUID = 8399791918484273089L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comunidad", nullable = false)
    private String comunity;

}
