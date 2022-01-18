package eus.solaris.solaris.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.SolarPanelRepository;

@Service
public class SolarPanelServiceImpl implements SolarPanelService {

    @Autowired
    private SolarPanelRepository solarPanelRepository;

    @Override
    public List<SolarPanel> findByUser(User user) {
        return solarPanelRepository.findByUser(user);
    }

}
