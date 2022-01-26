package eus.solaris.solaris.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "solar_panel")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class SolarPanel implements Serializable {

    private static final long serialVersionUID = -1422868069503118354L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "installed_on", nullable = false)
    private Instant timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id", nullable = false)
    private SolarPanelModel model;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provincia_id", nullable = false)
    @ToString.Exclude
    private Province province;

    @Version
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Integer version;
}
