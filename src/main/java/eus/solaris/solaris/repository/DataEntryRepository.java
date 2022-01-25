package eus.solaris.solaris.repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.SolarPanelDataEntry;

@Repository
public interface DataEntryRepository extends JpaRepository<SolarPanelDataEntry, Long> {
    @Query("SELECT d FROM SolarPanelDataEntry d WHERE d.solarPanel IN (?1) AND CAST(d.timestamp AS LocalDate) = ?2")
    public List<SolarPanelDataEntry> findBySolarPanelsAndLocalDate(List<SolarPanel> solarPanel, LocalDate day);

    @Query("SELECT d FROM SolarPanelDataEntry d WHERE d.solarPanel = ?1 AND d.timestamp >= ?2 AND d.timestamp <= ?3")
    public List<SolarPanelDataEntry> findBySolarPanelAndTimestampBetween(SolarPanel solarPanel, Instant start,
            Instant end);

    public List<SolarPanelDataEntry> findBySolarPanel(SolarPanel solarPanel);

    @Query("SELECT SUM(d.power) FROM SolarPanelDataEntry d WHERE d.solarPanel = ?1 AND d.timestamp >= ?2 AND d.timestamp <= ?3")
    public Double sumBySolarPanelAndTimestampBetween(SolarPanel solarPanel, Instant start, Instant end);
}
