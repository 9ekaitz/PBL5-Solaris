package eus.solaris.solaris.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.RequestDispatcher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import eus.solaris.solaris.config.SpringWebAuxTestConfig;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@Import(SpringWebAuxTestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest({ CustomErrorController.class })
class CustomErrorControllerTest {
  
  @Autowired
  MockMvc mvc;

  @MockBean
  LanguageServiceImpl languageServiceImpl;

  @MockBean
  UserServiceImpl userServiceImpl;

  @MockBean
  PasswordEncoder passwordEncoder;

  // @Test
  // void test404Error() throws Exception {
  //   mvc.perform(get("https://localhost/error")
  //   .param(RequestDispatcher.ERROR_STATUS_CODE, ""+HttpStatus.NOT_FOUND.value()))
  //   .andExpect(view().name("error/404"));
  // }

  // @Test
  // void test500Error() throws Exception {
  //   mvc.perform(get("https://localhost/error")
  //   .param(RequestDispatcher.ERROR_STATUS_CODE, ""+HttpStatus.INTERNAL_SERVER_ERROR.value()))
  //   .andExpect(view().name("error/500"));
  // }

  // @Test
  // void test403Error() throws Exception {
  //   mvc.perform(get("https://localhost/error")
  //   .param(RequestDispatcher.ERROR_STATUS_CODE, ""+HttpStatus.FORBIDDEN.value())
  //   .param(RequestDispatcher.ERROR_MESSAGE, "test"))
  //   .andExpect(view().name("error/403"))
  //   .andExpect(model().attributeExists("message", "error"));
  // }

  @Test
  void testOtherError() throws Exception {
    mvc.perform(get("https://localhost/error")
    .param(RequestDispatcher.ERROR_STATUS_CODE, ""+HttpStatus.BAD_REQUEST)
    .param(RequestDispatcher.ERROR_MESSAGE, "test"))
    .andExpect(view().name("error/generic-error"));
  }

}
