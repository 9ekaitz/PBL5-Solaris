package eus.solaris.solaris.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InstallerController {
  
  @GetMapping("/install")
  public String showInstallerPage(Model model) {
    return "page/installer.html";
  } 

}
