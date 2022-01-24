package eus.solaris.solaris.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "data_entry")
@Getter
@Setter
public class SolarPanelDataEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "power", nullable = false)
    private Double power;

    @Column(name = "voltage", nullable = false)
    private Double voltage;

    @Column(name = "current", nullable = false)
    private Double current;

    @ManyToOne
    @JoinColumn(name = "solar_panel_id", nullable = false)
    private SolarPanel solarPanel;
}
