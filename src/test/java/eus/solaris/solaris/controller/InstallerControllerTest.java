package eus.solaris.solaris.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import eus.solaris.solaris.config.SpringWebAuxTestConfig;
import eus.solaris.solaris.domain.Privilege;
import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.security.UserDetailsServiceImpl;
import eus.solaris.solaris.service.impl.InstallationServiceImpl;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.TaskServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InstallerController.class)
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

  @MockBean
  UserDetailsServiceImpl userDetailsServiceImpl;

  @Test
  public void accessToInstallPageWithoutCredentials() {
    try {
      mvc.perform(get("https://localhost/install")).andExpect(status().isFound());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Before
  public void loadAdminUser() {
    Role ROLE_ADMIN = new Role(1L, "ROLE_TEST", true, null, Stream
        .of(new Privilege(1L, "AUTH_INSTALL_VIEW", "auth.install.view", true, null, 1))
        .collect(Collectors.toSet()), 1);
    User adminUser = new User(4L, "testyAdmin", "testy@foo", "foo123", "Testy", "Tasty", "Admin", true, ROLE_ADMIN,
        null, 1);
    when(userServiceImpl.findByUsername("adminUser")).thenReturn(adminUser);
  }

  @Test
  @WithUserDetails("adminUser")
  public void accessToInstallPageWithWrongCredentials() {
    try {
      mvc.perform(get("https://localhost/install")).andExpect(status().isFound());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
