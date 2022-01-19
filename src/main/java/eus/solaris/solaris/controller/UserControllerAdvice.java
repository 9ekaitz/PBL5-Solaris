package eus.solaris.solaris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.UserService;

@ControllerAdvice
public class UserControllerAdvice {

  @Autowired
  UserService userService;

  @ModelAttribute("user")
  public User addUserToModel() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = null;
    if (auth != null) user = userService.findByUsername(auth.getName());
    return user;
  }

}
