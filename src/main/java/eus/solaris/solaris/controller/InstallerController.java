package eus.solaris.solaris.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import eus.solaris.solaris.domain.Installation;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.TaskForm;
import eus.solaris.solaris.service.InstallationService;
import eus.solaris.solaris.service.TaskService;

@Controller
@RequestMapping("/install")
public class InstallerController {

  @Autowired
  InstallationService installationService;

  @Autowired
  TaskService taskService;

  @PreAuthorize("hasAuthority('AUTH_INSTALL_READ')")
  @GetMapping
  public String showInstallerPage(Model model) {
    User user = (User) model.getAttribute("user");

    List<Installation> pendingInstallations = installationService.findByInstallerAndCompleted(user, false);
    List<Installation> completedInstallations = installationService.findByInstallerAndCompleted(user, true);

    model.addAttribute("pendingInstallations", pendingInstallations);
    model.addAttribute("completedInstallations", completedInstallations);

    return "page/installer";
  }

  @PreAuthorize("hasAuthority('AUTH_INSTALL_READ')")
  @GetMapping(value = "/{id}")
  public String showTask(@PathVariable(value = "id") Long id, Model model) {

    Installation installation = installationService.findById(id);

    if (!filter( installation, (User) model.getAttribute("user")))
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    model.addAttribute("installation", installation);

    return "page/installation";
  }

  @PreAuthorize("hasAuthority('AUTH_INSTALL_WRITE')")
  @PostMapping(value = "/{id}/save")
  public String saveTask(@PathVariable(value = "id") Long id, @ModelAttribute TaskForm form, Model model) {
    if (!filter(installationService.findById(id), (User) model.getAttribute("user")))
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    if (form.getTasksId() != null)
      form.getTasksId().stream()
          .filter(Objects::nonNull)
          .forEach(i -> taskService.markCompleted(taskService.findById(i)));
    model.addAttribute("installation", installationService.findById(id));
    return "redirect:/install/" + id;
  }

  private boolean filter(Installation installation, User user) {
    boolean authorized = false;
    if (user != null && user.getRole().getName().equals("ROLE_ADMIN") || installation.getInstaller() == user)
      authorized = true;

    return authorized;
  }
}
