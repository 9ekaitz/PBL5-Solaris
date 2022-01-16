package eus.solaris.solaris.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

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

  Role ROLE_ADMIN;

  @Test
  public void accessToInstallPageWithoutCredentials() {
    try {
      mvc.perform(get("https://localhost/install")).andExpect(status().isFound());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Before
  void loadUser(){
    String username = "testy";
    User user = new User(1L, "testy", "tetsy@email.com", "testy123", "Testy", "Tasty", "Tester", true, null, null, 1);
    when(userServiceImpl.findByUsername(username)).thenReturn(user);
  }
  

  @Test
  @WithMockUser(authorities = {"AUTH_INSTALL_VIEW"})
  public void accessToInstallPageWithWrongCredentials() throws Exception{
      mvc.perform(get("https://localhost/install")).andExpect(status().isFound());
  }
}
