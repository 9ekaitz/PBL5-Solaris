package eus.solaris.solaris.controller;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.UserService;

@Controller
public class InstallerController {
  
  @Autowired
  UserService userService;


  @GetMapping("/install")
  public String showInstallerPage(Authentication authentication, Model model) {
    model.addAttribute("page_title", "GENERAL");
    if (authentication == null) return "page/login";
    User user = userService.findByUsername(authentication.getName());

    /* TODO: Remove manually setted role */
    Role role = new Role();
    role.setName("admin");
    user.setRole(role);
    /* --------------------------------- */
    
    model.addAttribute("user", user);
    return "page/installer";
  } 

}
