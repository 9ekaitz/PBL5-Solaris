package eus.solaris.solaris.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.dto.UserRegistrationDto;
import eus.solaris.solaris.service.RoleService;
import eus.solaris.solaris.service.UserService;

@Controller
public class AppController {
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;


	@GetMapping("/")
	public String index(Model model, Authentication authentication) {
		String name = authentication.getName();
		User user = userService.findByUsername(name);
		if (user != null)
			model.addAttribute("user", user);
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());

		return "login";
	}

	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("user", new UserRegistrationDto());

		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") UserRegistrationDto dto, BindingResult result, Model model) {
		User user = modelMapper.map(dto, User.class);
		user.setRole(roleService.findByName("ROLE_ADMIN"));
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.save(user);
		return "login";
	}

}
