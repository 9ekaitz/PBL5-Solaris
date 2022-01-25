package eus.solaris.solaris.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.SolarPanelRepository;
import eus.solaris.solaris.service.SolarPanelService;

@Service
public class SolarPanelServiceImpl implements SolarPanelService {

    private static final long serialVersionUID = -715845923957230652L;
    @Autowired
    private SolarPanelRepository solarPanelRepository;

    @Override
    public List<SolarPanel> findByUser(User user) {
        return solarPanelRepository.findByUser(user);
    }

}
