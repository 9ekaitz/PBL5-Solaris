package eus.solaris.solaris.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import eus.solaris.solaris.domain.Task;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.DataEntryRepository;
import eus.solaris.solaris.repository.SolarPanelRepository;
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
  SolarPanelRepository solarPanelRespository;

  @MockBean
  DataEntryRepository dataEntryRepository;

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
    Long requestID = 1L;

    try {
      mvc.perform(get("https://localhost/install/" + requestID))
          .andExpect(status().isFound());
    } catch (Exception e) {
    }
  }

  @Test
  @WithUserDetails(value = "testyAdmin")
  void accessToInstallWithAdmin() {
    Long requestID = 1L;

    Installation installation = new Installation(requestID, "Install_Name 1", "Install_Desc 1", true, null, null, null,
        1);
    when(installationServiceImpl.findById(1L)).thenReturn(installation);

    try {
      mvc.perform(get("https://localhost/install/" + requestID))
          .andExpect(status().isOk())
          .andExpect(model().attribute("installation", installation));
    } catch (Exception e) {
    }
  }

  @Test
  @WithUserDetails(value = "testyTechnician")
  void accessToInstallWithCredentials() {
    Long requestID = 1L;

    Installation installation = new Installation(requestID, "Install_Name 1", "Install_Desc 1", true, null, user, null,
        1);
    when(installationServiceImpl.findById(1L)).thenReturn(installation);

    try {
      mvc.perform(get("https://localhost/install/" + requestID))
          .andExpect(status().isOk());
    } catch (Exception e) {
    }
  }

  @Test
  @WithUserDetails(value = "testyTechnician")
  void accesToInstallWithWrongCredentials() {
    Long requestID = 1L;
    User testUser = new User();
    testUser.setId(10L);
    Installation installation = new Installation(requestID, "Install_Name 1", "Install_Desc 1", true, null, testUser,
        null, 1);
    when(installationServiceImpl.findById(1L)).thenReturn(installation);

    try {
      mvc.perform(get("https://localhost/install/" + requestID))
          .andExpect(status().isForbidden());
    } catch (Exception e) {
    }
  }

  @Test
  void postToInstallWithoutAuth() {
    Long requestID = 1L;

    try {
      mvc.perform(post("https://localhost/install/" + requestID + "/save")
          .with(csrf()))
          .andExpect(status().isFound());
    } catch (Exception e) {
    }
    verifyNoInteractions(installationServiceImpl);
  }

  @Test
  @WithUserDetails(value = "testyTechnician")
  void postToInstallPageWithCredentialsAndOneNullParameter() {
    Long requestID = 1L;

    Installation installation = new Installation(requestID, "Install_Name 1", "Install_Desc 1", true, null, user, null,
        1);

    Task task1 = new Task(1L, "description", false, installation, 0);
    Task task2 = new Task(2L, "description", false, installation, 0);
    Task task1T = new Task(1L, "description", true, installation, 0);
    Task task2T = new Task(2L, "description", true, installation, 0);

    when(installationServiceImpl.findById(requestID)).thenReturn(installation);
    when(taskServiceImpl.findById(1L)).thenReturn(task1);
    when(taskServiceImpl.findById(2L)).thenReturn(task2);

    when(taskServiceImpl.markCompleted(task1)).thenReturn(task1T);
    when(taskServiceImpl.markCompleted(task2)).thenReturn(task2T);

    try {
      mvc.perform(post("https://localhost/install/" + requestID + "/save")
          .param("tasksId[0]", "1")
          .param("tasksId[1]", "")
          .param("tasksId[2]", "2")
          .with(csrf()))
          .andExpect(status().isFound())
          .andExpect(view().name("redirect:/install/" + requestID));
    } catch (Exception e) {
      e.printStackTrace();
    }
    verify(taskServiceImpl, times(1)).findById(1L);
    verify(taskServiceImpl, times(1)).findById(2L);
    verify(taskServiceImpl, times(1)).markCompleted(task1);
    verify(taskServiceImpl, times(1)).markCompleted(task2);
    verifyNoMoreInteractions(taskServiceImpl);
  }

  @Test
  @WithUserDetails(value = "testyAdmin")
  void postToInstallPageWithAdmin() {
    Long requestID = 1L;

    User testUser = new User();
    testUser.setId(10L);

    Installation installation = new Installation(requestID, "Install_Name 1", "Install_Desc 1", true, null, testUser,
        null, 1);

    when(installationServiceImpl.findById(requestID)).thenReturn(installation);

    try {
      mvc.perform(post("https://localhost/install/" + requestID + "/save")
          .param("tasksId[0]", "1")
          .with(csrf()))
          .andExpect(status().isFound())
          .andExpect(view().name("redirect:/install/" + requestID));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @WithUserDetails(value = "testyTechnician")
  void postToInstallWithWrongCredentials() {
    Long requestID = 1L;
    User testUser = new User();
    testUser.setId(10L);
    Installation installation = new Installation(requestID, "Install_Name 1", "Install_Desc 1", true, null, testUser,
        null, 1);
    when(installationServiceImpl.findById(1L)).thenReturn(installation);

    try {
      mvc.perform(post("https://localhost/install/" + requestID + "/save")
          .with(csrf()))
          .andExpect(status().isForbidden());
    } catch (Exception e) {
    }
  }

}
