package eus.solaris.solaris.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import eus.solaris.solaris.domain.Task;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.TaskForm;
import eus.solaris.solaris.repository.ImageRepository;
import eus.solaris.solaris.service.InstallationService;
import eus.solaris.solaris.service.TaskService;

@Controller
@RequestMapping("/install")
public class InstallerController {

  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private static final String INSTALL_REDIRECT = "redirect:/install";

  @Autowired
  InstallationService installationService;

  @Autowired
  TaskService taskService;

  @Autowired
  ImageRepository imageRepository;

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

    if (!filter(installation, (User) model.getAttribute("user")))
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    if (installation == null || Boolean.TRUE.equals(installation.getCompleted())) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    model.addAttribute("installation", installation);

    return "page/installation";
  }

  @PreAuthorize("hasAuthority('AUTH_INSTALL_WRITE')")
  @PostMapping(value = "/{id}/save", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public String saveTask(@PathVariable(value = "id") Long id, @ModelAttribute TaskForm form, Model model) {
    String redirect = INSTALL_REDIRECT + "/" + id;

    Installation installation = installationService.findById(id);
    if (!filter(installation, (User) model.getAttribute("user")))
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    if (form.getTasksId() != null)
      form.getTasksId().stream()
          .filter(Objects::nonNull)
          .forEach(i -> taskService.markCompleted(taskService.findById(i)));

    if (checkTaskCompleted(installation)) {
      try {
        installation.setSign(imageRepository.save(form.getSign()));
        installation.setCompleted(true);
        installationService.save(installation);
        redirect = INSTALL_REDIRECT;
      } catch (Exception e) {}
    }
    
    return redirect;
  }

  private boolean filter(Installation installation, User user) {
    boolean authorized = false;
    if (user != null && user.getRole().getName().equals(ROLE_ADMIN) || installation.getInstaller() == user)
      authorized = true;

    return authorized;
  }

  private boolean checkTaskCompleted(Installation installation) {
    boolean completed = true;
    if (installation.getTasks() != null) {
      for (Task task : installation.getTasks()) {
        if (Boolean.FALSE.equals(task.getCompleted()))
          completed = false;
      }
    }

    return completed;
  }
}