package eus.solaris.solaris.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.thymeleaf.exceptions.TemplateProcessingException;

import eus.solaris.solaris.config.SpringWebAuxTestConfig;
import eus.solaris.solaris.domain.Privilege;
import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.impl.InstallationServiceImpl;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.TaskServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@Import(SpringWebAuxTestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest({ InstallerController.class, UserControllerAdvice.class })
public class InstallerControllerTest {

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

  @Test
  public void accessToInstallPageWithoutCredentials() {
    try {
      mvc.perform(get("https://localhost/install")).andExpect(status().isFound());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @WithUserDetails(value = "testyUser")
  public void accessToInstallPageWithWrongCredentials() {
    try {
      mvc.perform(get("https://localhost/install")).andExpect(status().isForbidden());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // @Before
  // void loadUser(){
  // String username = "adminUser";
  // when(userServiceImpl.findByUsername(username)).thenReturn(userDetailsService.loadUserByUsername(username));
  // }
  User adminUser;

  @Before
  void loadUser() {
    String username = "adminUser";
    Role ROLE_ADMIN = new Role(1L, "ROLE_ADMIN", true, null, Stream
        .of(new Privilege(1L, "AUTH_INSTALL_VIEW", "auth.install.view", true, null, 1))
        .collect(Collectors.toSet()), 1);

    adminUser = new User(4L, "testyAdmin", "testy@foo", "foo123", "Testy", "Tester", "Admin", true, ROLE_ADMIN,
        null, 1);
    when(userServiceImpl.findByUsername(username)).thenReturn(adminUser);
  }

  @Test
  @WithUserDetails(value = "testyAdmin")
  public void accessToInstallPageWithCredentials() {
    try {
      mvc.perform(get("https://localhost/install"))
          .andExpect(status().isOk())
          .andExpect(view().name("page/install"))
          .andExpect(model().attributeExists("user"));

    } catch (TemplateProcessingException e) {

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
