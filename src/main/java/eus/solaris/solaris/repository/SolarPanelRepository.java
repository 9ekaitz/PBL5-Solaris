package eus.solaris.solaris.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.solaris.solaris.domain.SolarPanel;

public interface SolarPanelRepository extends JpaRepository<SolarPanel, Long> {
}
