package eus.solaris.solaris.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

  @PreAuthorize("hasAuthority('AUTH_INSTALL_VIEW')")
  @GetMapping
  public String showInstallerPage(Authentication authentication, Model model) {
    if (authentication == null) return "page/login";
    User user = userService.findByUsername(authentication.getName());

    List<Installation> pendingInstallations = installationService.findByInstallerAndCompleted(user, false);
    List<Installation> completedInstallations = installationService.findByInstallerAndCompleted(user, true);
    model.addAttribute("user", user);
    model.addAttribute("pendingInstallations", pendingInstallations);
    model.addAttribute("completedInstallations", completedInstallations);
  
    return "page/installer";
  }
}
