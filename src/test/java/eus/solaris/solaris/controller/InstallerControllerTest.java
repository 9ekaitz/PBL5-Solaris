package eus.solaris.solaris.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import eus.solaris.solaris.domain.Installation;
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
  void setup() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      username = authentication.getName();
      user = ((CustomUserDetails) userDetailsService.loadUserByUsername(username)).getuser();
      when(userServiceImpl.findByUsername(username)).thenReturn(user);
    }
  }

  @Test
  void accessToDashboardWithoutAuth() {
    try {
      mvc.perform(get("https://localhost/install")).andExpect(status().isFound());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @WithUserDetails(value = "testyUser")
  void accessToDashboardWithWrongCredentials() {
    try {
      mvc.perform(get("https://localhost/install")).andExpect(status().isForbidden());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @WithUserDetails(value = "testyTechnician")
  void accessToDashboardWithCredentials() {
    List<Installation> pending = Stream
        .of(new Installation(1L, "pending_install 1", "Install_Desc 1", true, null, null, null,
            1))
        .collect(Collectors.toList());

    List<Installation> completed = Stream
        .of(new Installation(2L, "completed_install 1", "Install_Desc 1", true, null, null,
            null, 1))
        .collect(Collectors.toList());

    when(installationServiceImpl.findByInstallerAndCompleted(user, false))
        .thenReturn(pending);
    when(installationServiceImpl.findByInstallerAndCompleted(user, true))
        .thenReturn(completed);

    try {
      mvc.perform(get("https://localhost/install"))
          .andExpect(status().isOk())
          .andExpect(view().name("page/installer"))
          .andExpect(model().attribute("pendingInstallations", pending))
          .andExpect(model().attribute("completedInstallations", completed));
    } catch (Exception e) {
      e.printStackTrace();
    }
    verify(userServiceImpl, times(1)).findByUsername(username);
  }

  @Test
  void accessToInstallWithoutAuth() {
    try {
      mvc.perform(get("https://localhost/install/1"))
          .andExpect(status().isFound());
    } catch (Exception e) {
    }
  }

  @Test
  @WithUserDetails(value = "testyAdmin")
  void accesToInstallWithAdmin() {

    Installation installation = new Installation(1L, "Install_Name 1", "Install_Desc 1", true, null, null, null, 1);
    when(installationServiceImpl.findById(1L)).thenReturn(installation);

    try {
      mvc.perform(get("https://localhost/install/1"))
          .andExpect(status().isOk())
          .andExpect(model().attribute("installation", installation));
    } catch (Exception e) {
    }
  }

  @Test
  @WithUserDetails(value = "testyTechnician")
  void accesToInstallWithCredentials() {

    Installation installation = new Installation(1L, "Install_Name 1", "Install_Desc 1", true, null, user, null, 1);
    when(installationServiceImpl.findById(1L)).thenReturn(installation);

    try {
      mvc.perform(get("https://localhost/install/1"))
          .andExpect(status().isOk())
          .andExpect(model().attribute("installation", installation));
    } catch (Exception e) {
    }
  }

  @Test
  @WithUserDetails(value = "testyTechnician")
  void accesToInstallWithWrongCredentials() {
    User testUser = new User();
    testUser.setId(10L);
    Installation installation = new Installation(1L, "Install_Name 1", "Install_Desc 1", true, null, testUser, null, 1);
    when(installationServiceImpl.findById(1L)).thenReturn(installation);

    try {
      mvc.perform(get("https://localhost/install/1"))
          .andExpect(status().isForbidden());
    } catch (Exception e) {
    }
  }

  // @Test
  // @WithUserDetails(value = "testyAdmin")
  // void postToInstallPageWithCredentials() {
  // try {
  // mvc.perform(post("https://localhost/install").param("test",
  // "test").with(RequestPostProcessor.))
  // .andExpect(status().isOk())
  // .andExpect(view().name("page/installer"))
  // .andExpect(model().attribute("user", user));
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // verify(userServiceImpl, times(1)).findByUsername(username);
  // }

}
