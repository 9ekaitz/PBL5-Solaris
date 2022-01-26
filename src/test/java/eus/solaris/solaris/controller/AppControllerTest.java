package eus.solaris.solaris.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import eus.solaris.solaris.config.SpringWebAuxTestConfig;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.DataEntryRepository;
import eus.solaris.solaris.repository.SolarPanelRepository;
import eus.solaris.solaris.security.CustomUserDetails;
import eus.solaris.solaris.security.UserDetailsServiceImpl;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.RoleServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@Import(SpringWebAuxTestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AppController.class)
class AppControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  UserServiceImpl userServiceImpl;

  @MockBean
  RoleServiceImpl roleServiceImpl;

  @MockBean
  LanguageServiceImpl languageServiceImpl;

  @MockBean
  MessageSource messageSource;

  @MockBean
  SolarPanelRepository solarPanelRespository;

  @MockBean
  DataEntryRepository dataEntryRepository;

  @MockBean
  PasswordEncoder passwordEncoder;

  @MockBean
  UserDetailsServiceImpl userDetailsServiceImpl;

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
  @WithMockUser("testy")
  void returnIndexPage() {
    try {
      mvc.perform(get("https://localhost/"))
          .andExpect(status().isOk())
          .andExpect(view().name("page/index"))
          .andExpect(model().attribute("user", user));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void returnIndexPageWithOutBeingLogedIn() {
    try {
      mvc.perform(get("https://localhost/"))
          .andExpect(status().isOk())
          .andExpect(view().name("page/index"))
          .andExpect(model().attributeDoesNotExist("user"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void loginTestWithoutUser() throws Exception{
      mvc.perform(get("https://localhost/login"))
          .andExpect(status().isOk())
          .andExpect(view().name("page/login"));
  }

  @Test
  void registerFormGetWithoutUser() throws Exception{
      mvc.perform(get("https://localhost/register"))
          .andExpect(status().isOk())
          .andExpect(model().attributeExists("form"));
  }

  @Test
  void registerFormWithErrors() throws Exception {

    mvc.perform(post("https://localhost/register")
    .param("username", "test")
    .with(csrf()))
    .andExpect(status().isOk())
    .andExpect(model().attributeExists("errors"))
    .andExpect(model().attributeExists("form"));
  }

  @Test
  void registerFormUserExist() throws Exception {

    User u = createUser();

    when(userServiceImpl.findByUsername(u.getUsername())).thenReturn(u);

    mvc.perform(post("https://localhost/register")
    .param("username", "test")
    .param("password", "123456789aA@")
    .param("verifyPassword", "123456789aA@")
    .param("name", "test")
    .param("firstSurname", "test")
    .param("secondSurname", "test")
    .param("email", "valid@email.com")
    .with(csrf()))
    .andExpect(status().isOk())
    .andExpect(model().attributeExists("errors"))
    .andExpect(model().attributeExists("form"));
  }

  @Test
  void registerFormNullUser() throws Exception {

    mvc.perform(post("https://localhost/register")
    .param("username", "")
    .param("password", "123456789aA@")
    .param("verifyPassword", "123456789aA@")
    .param("name", "test")
    .param("firstSurname", "test")
    .param("secondSurname", "test")
    .param("email", "valid@email.com")
    .with(csrf()))
    .andExpect(status().isOk())
    .andExpect(model().attributeExists("errors"))
    .andExpect(model().attributeExists("form"));
  }

  @Test
  void registerFormSuccess() throws Exception {

    mvc.perform(post("https://localhost/register")
    .param("username", "test")
    .param("password", "123456789aA@")
    .param("verifyPassword", "123456789aA@")
    .param("name", "test")
    .param("firstSurname", "test")
    .param("secondSurname", "test")
    .param("email", "valid@email.com")
    .with(csrf()))
    .andExpect(status().isOk())
    .andExpect(view().name("page/login"));
  }

  private User createUser() {
    User u = new User();
    
    u.setId(1L);
    u.setUsername("test");

    return u;
  }

}