package eus.solaris.solaris.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.Task;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.TaskService;
import eus.solaris.solaris.service.UserService;

@Controller
@RequestMapping("/install")
public class InstallerController {
  
  @Autowired
  UserService userService;

  @Autowired
  TaskService taskService;


  @GetMapping
  public String showInstallerPage(Authentication authentication, Model model) {
    model.addAttribute("page_title", "GENERAL");
    if (authentication == null) return "page/login";
    User user = userService.findByUsername(authentication.getName());

    /* TODO: Remove manually setted role */
    Role role = new Role();
    role.setName("admin");
    user.setRole(role);
    /* --------------------------------- */

    List<Task> pendingTasks = taskService.findByInstallerAndCompleted(user, false);
    List<Task> completedTasks = taskService.findByInstallerAndCompleted(user, true);
    model.addAttribute("user", user);
    model.addAttribute("pendingTasks", pendingTasks);
    model.addAttribute("completedTasks", completedTasks);
  
    return "page/installer";
  }

  @GetMapping(value = "/task/{id}")
  public String showTask(@PathVariable(value = "id") Long id){
    return "";
  }

}
