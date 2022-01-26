package eus.solaris.solaris.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.SolarPanelModel;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.SolarPanelRepository;
import eus.solaris.solaris.service.impl.SolarPanelServiceImpl;

@ExtendWith(MockitoExtension.class)
class SolarPanelServiceImplTest {

  @InjectMocks
  private SolarPanelServiceImpl solarPanelServiceImpl;

  @Mock
  private SolarPanelRepository solarPanelRepository;

  @Test
  void saveTest() {
    SolarPanel solarPanel = new SolarPanel();
    solarPanel.setId(1L);

    when(solarPanelRepository.save(solarPanel)).thenReturn(solarPanel);
    assertEquals(solarPanel, solarPanelServiceImpl.save(solarPanel));
  }

  @Test
  void createTest() {
    SolarPanelModel solarPanelModel = new SolarPanelModel();
    User user = new User();
    Province p = new Province();
    SolarPanel solarPanel = new SolarPanel(1L, Instant.now(), solarPanelModel, user, p, 0);


    when(solarPanelRepository.save(any())).thenReturn(solarPanel);
    assertEquals(solarPanel, solarPanelServiceImpl.create(solarPanelModel, user, p));
  }

  @Test
  void findByUserTest() {
    User user = new User();
    Province p = new Province();
    SolarPanel solarPanel = new SolarPanel(1L, Instant.now(), new SolarPanelModel(), user, p, 0);
    List<SolarPanel> lst = Stream.of(solarPanel).collect(Collectors.toList());

    when(solarPanelRepository.findByUser(user)).thenReturn(lst);
    assertEquals(lst, solarPanelServiceImpl.findByUser(user));
  }
}
