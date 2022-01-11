package eus.solaris.solaris.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.SolarPanelDataEntry;

@Repository
public interface DataEntryRepository extends JpaRepository<SolarPanelDataEntry, Long> {
    @Query("SELECT d FROM SolarPanelDataEntry d WHERE d.solarPanel = ?1 AND CAST(d.timestamp AS LocalDate) = ?2")
    public List<SolarPanelDataEntry> findBySolarPanelAndLocalDate(SolarPanel solarPanel, LocalDate day);
}
