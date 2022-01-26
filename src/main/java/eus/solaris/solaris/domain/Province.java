package eus.solaris.solaris.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "relacion_comunidad_provincia")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class Province implements Serializable {

    private static final long serialVersionUID = 2013940427874671032L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_provincia")
    private Long id;

    @Column(name = "code", nullable = false, length = 64, unique = true)
    private String code;

    @Column(name = "i18n", nullable = false, length = 64, unique = true)
    private String i18n;

    @Column(name = "capital_provincia", nullable = false)
    private String capitalProvincia;

    @ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_comunidad", nullable = false)
    @EqualsAndHashCode.Exclude
    private Comunidad comunidad;

    @Version
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Integer version;
}
