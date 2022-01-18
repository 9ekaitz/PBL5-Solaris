package eus.solaris.solaris.service;

import java.util.List;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.User;

public interface SolarPanelService {
    public List<SolarPanel> findByUser(User user);
}
