package eus.solaris.solaris.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.security.CustomUserDetails;
import eus.solaris.solaris.service.impl.InstallationServiceImpl;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.TaskServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@Import(SpringWebAuxTestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest({ InstallerController.class, UserControllerAdvice.class })
class InstallerControllerTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  InstallationServiceImpl installationServiceImpl;

  @MockBean
  TaskServiceImpl taskServiceImpl;

  @MockBean
  LanguageServiceImpl languageServiceImpl;

  @MockBean
  UserServiceImpl userServiceImpl;

  @MockBean
  PasswordEncoder passwordEncoder;

  @Autowired
  UserDetailsService userDetailsService;

  private User user;
  private String username;

  @BeforeEach
  void setup(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      username = authentication.getName();
      user = ((CustomUserDetails) userDetailsService.loadUserByUsername(username)).getuser();
      when(userServiceImpl.findByUsername(username)).thenReturn(user);
    }
  }

  @Test
  void accessToInstallPageWithoutCredentials() {
    try {
      mvc.perform(get("https://localhost/install")).andExpect(status().isFound());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @WithUserDetails(value = "testyUser")
  void accessToInstallPageWithWrongCredentials() {
    try {
      mvc.perform(get("https://localhost/install")).andExpect(status().isForbidden());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @WithUserDetails(value = "testyAdmin")
  void accessToInstallPageWithCredentials() {
    try {
      mvc.perform(get("https://localhost/install"))
          .andExpect(status().isOk())
          .andExpect(view().name("page/installer"))
          .andExpect(model().attribute("user", user));
    } catch (Exception e) {
      e.printStackTrace();
    }
    verify(userServiceImpl, times(1)).findByUsername(username);
  }
}
