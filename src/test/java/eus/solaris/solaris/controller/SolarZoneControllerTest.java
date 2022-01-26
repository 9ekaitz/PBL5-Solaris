package eus.solaris.solaris.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import eus.solaris.solaris.config.SpringWebAuxTestConfig;
import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.security.CustomUserDetails;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.SolarPanelServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@Import(SpringWebAuxTestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest({ SolarZoneController.class, UserControllerAdvice.class })
public class SolarZoneControllerTest {
  
  private final static String URL_SOLARZONE = "https://localhost/solarzone";
  private final static String URL_PANELS = "https://localhost/solarzone/panel";
  private final static String URL_ECONOMIC = "https://localhost/solarzone/economic";
  private final static String URL_ECO = "https://localhost/solarzone/eco";
  
  @Autowired
  MockMvc mvc;

  @MockBean
  LanguageServiceImpl languageService;

  @MockBean
  SolarPanelServiceImpl solarPanelService;

  @MockBean
  UserServiceImpl userService;

  @MockBean
  PasswordEncoder passwordEncoder;

  @Autowired
  UserDetailsService userDetailsService;

  private User user;
  private String username;

  @BeforeEach
  void setup() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      username = authentication.getName();
      user = ((CustomUserDetails) userDetailsService.loadUserByUsername(username)).getuser();
      when(userService.findByUsername(username)).thenReturn(user);
    }
  }

  @ParameterizedTest
  @ValueSource(strings = {URL_SOLARZONE, URL_PANELS, URL_ECONOMIC, URL_ECO})
  void solarzoneWithoutUser(String url) throws Exception {
    mvc.perform(get(url))
    .andExpect(status().isFound());
  }

  @ParameterizedTest
  @ValueSource(strings = {URL_SOLARZONE, URL_PANELS, URL_ECONOMIC, URL_ECO})
  @WithUserDetails("testyAdmin")
  void solarzoneWithUser(String url) throws Exception {
    SolarPanel p = createsolarPanel();

    List<SolarPanel> panels = Stream.of(p).collect(Collectors.toList());

    when(solarPanelService.findByUser(user)).thenReturn(panels);
    mvc.perform(get(url))
    .andExpect(status().isOk())
    .andExpect(model().attribute("panels", panels));
  }

  @ParameterizedTest
  @ValueSource(strings = {URL_SOLARZONE, URL_PANELS, URL_ECONOMIC, URL_ECO})
  @WithUserDetails("testyAdmin")
  void solarzoneWithUserNoPanels(String url) throws Exception {
    when(solarPanelService.findByUser(user)).thenReturn(Collections.emptyList());
    
    mvc.perform(get(url))
    .andExpect(status().isUnauthorized());
  }

  private SolarPanel createsolarPanel() {
    SolarPanel p = new SolarPanel();
    p.setId(1L);

    return p;
  }

}
