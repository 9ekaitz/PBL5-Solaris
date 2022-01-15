package eus.solaris.solaris.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.security.UserDetailsServiceImpl;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.RoleServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(AppController.class)
class AppControllerTest {

  @Autowired
  private MockMvc mvc;

  @InjectMocks
  AppController appController;

  @Mock
  Authentication authentication;

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

  @Test
  public void returnIndexPage() {
    String username = "testy";
    User user = new User(1L, "testy", "tetsy@email.com", "testy123", "Testy", "Tasty", "Tester", true, null, null, 1);
    when(authentication.getName()).thenReturn(username);
    when(userServiceImpl.findByUsername(username)).thenReturn(user);
    try {
      mvc.perform(get("https://localhost/"))
          .andExpect(status().isOk())
          .andExpect(view().name("page/index"))
          .andExpect(model().attribute("user", user));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
