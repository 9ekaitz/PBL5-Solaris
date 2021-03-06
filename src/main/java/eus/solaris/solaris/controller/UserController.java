package eus.solaris.solaris.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public String get(@RequestParam String name, Model model) {
        User user = userRepository.findByUsernameIgnoreCase(name);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = "/{id}")
    public User getId(@PathVariable(value = "id") Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/all")
    public String all(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

}
