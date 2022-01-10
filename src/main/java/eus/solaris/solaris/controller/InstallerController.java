package eus.solaris.solaris.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.Installation;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.InstallationService;
import eus.solaris.solaris.service.UserService;

@Controller
@RequestMapping("/install")
public class InstallerController {
  
  @Autowired
  UserService userService;

  @Autowired
  InstallationService installationService;

  @GetMapping
  public String showInstallerPage(Model model) {
    User user = (User) model.getAttribute("user");

    List<Installation> pendingInstallations = installationService.findByInstallerAndCompleted(user, false);
    List<Installation> completedInstallations = installationService.findByInstallerAndCompleted(user, true);
    
    model.addAttribute("pendingInstallations", pendingInstallations);
    model.addAttribute("completedInstallations", completedInstallations);
  
    return "page/installer";
  }

  @GetMapping(value = "/installation/{id}")
  public String showTask(@PathVariable(value = "id") Long id, Model model){
    model.addAttribute("installation", installationService.findById(id));

    return "page/installation";
  }

}
