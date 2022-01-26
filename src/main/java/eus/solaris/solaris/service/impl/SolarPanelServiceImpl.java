package eus.solaris.solaris.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.SolarPanelModel;
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

    @Override
    public SolarPanel save(SolarPanel solarPanel) {
        return solarPanelRepository.save(solarPanel);
    }

    @Override
    public SolarPanel create(SolarPanelModel model, User owner, Province location) {
        SolarPanel solarPanel = new SolarPanel();
		solarPanel.setModel(model);
		solarPanel.setUser(owner);
		solarPanel.setTimestamp(Instant.now());
		solarPanel.setProvince(location);
        return solarPanelRepository.save(solarPanel);
    }

}
