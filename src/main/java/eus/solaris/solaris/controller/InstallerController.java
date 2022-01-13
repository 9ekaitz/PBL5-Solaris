package eus.solaris.solaris.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.solaris.solaris.domain.Installation;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.TaskForm;
import eus.solaris.solaris.service.InstallationService;
import eus.solaris.solaris.service.TaskService;
import eus.solaris.solaris.service.UserService;

@Controller
@RequestMapping("/install")
public class InstallerController {
  
  @Autowired
  UserService userService;

  @Autowired
  InstallationService installationService;

  @Autowired
  TaskService taskService;

  @PreAuthorize("hasAuthority('AUTH_INSTALL_VIEW')")
  @GetMapping
  public String showInstallerPage(Authentication authentication, Model model) {
    User user = (User) model.getAttribute("user");

    List<Installation> pendingInstallations = installationService.findByInstallerAndCompleted(user, false);
    List<Installation> completedInstallations = installationService.findByInstallerAndCompleted(user, true);
    
    model.addAttribute("pendingInstallations", pendingInstallations);
    model.addAttribute("completedInstallations", completedInstallations);
  
    return "page/installer";
  }

  @GetMapping(value = "/{id}")
  public String showTask(@PathVariable(value = "id") Long id, Model model){
    model.addAttribute("installation", installationService.findById(id));

    return "page/installation";
  }

  @PostMapping(value = "/save/{id}")
  public String saveTask(@PathVariable(value = "id") Long id, @ModelAttribute TaskForm form, Model model){
    if (form.getTasksId() != null) form.getTasksId().stream().filter((i)-> i != null).forEach((i)-> taskService.markCompleted(taskService.findById(i)));
    model.addAttribute("installation", installationService.findById(id));
    return "redirect:/install/"+id;
  } 
}
