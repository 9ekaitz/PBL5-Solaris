package eus.solaris.solaris.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.User;

public interface SolarPanelRepository extends JpaRepository<SolarPanel, Long> {
    public List<SolarPanel> findByUser(User user);
}
