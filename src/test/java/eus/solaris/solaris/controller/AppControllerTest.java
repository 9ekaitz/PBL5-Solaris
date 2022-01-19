package eus.solaris.solaris.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.security.UserDetailsServiceImpl;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.RoleServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AppController.class)
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
  PasswordEncoder passwordEncoder;

  @MockBean
  UserDetailsServiceImpl userDetailsServiceImpl;

  private User user;

  @BeforeEach
  void loadUser(){
    String username = "testy";
    user = new User(1L, "testy", "tetsy@email.com", "testy123", "Testy", "Tasty", "Tester", true, null, null, 1);
    when(userServiceImpl.findByUsername(username)).thenReturn(user);
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

}
