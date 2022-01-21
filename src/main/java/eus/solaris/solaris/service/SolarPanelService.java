package eus.solaris.solaris.service;

import java.io.Serializable;
import java.util.List;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.User;

public interface SolarPanelService extends Serializable {
    public List<SolarPanel> findByUser(User user);
}