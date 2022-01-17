package eus.solaris.solaris.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "province")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class Province implements Serializable{

    private static final long serialVersionUID = 2013940427874671032L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 64)
    private String code;

    @Column(name = "i18n", nullable = false, length = 64)
    private String i18n;

    @Version
    @lombok.EqualsAndHashCode.Exclude
    private Integer version;
}
