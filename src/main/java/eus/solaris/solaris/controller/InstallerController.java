package eus.solaris.solaris.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.User;

@Controller
public class InstallerController {
  
  @GetMapping("/install")
  public String showInstallerPage(Model model) {
    model.addAttribute("page_title", "GENERAL");
    User test = new User();
    Role role = new Role();
    role.setName("installer");
    test.setRole(role);
    model.addAttribute("user", test);
    return "page/installer.html";
  } 

}
