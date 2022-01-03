package eus.solaris.solaris.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.dto.UserRegistrationDto;
import eus.solaris.solaris.service.LanguageService;
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

	@Autowired
	LanguageService languageService;

	@ModelAttribute
	public void addLanguages(Model model) {
		model.addAttribute("languages", languageService.findAll());
	}


	@GetMapping("/")
	public String index(Model model, Authentication authentication) {
		if(authentication != null) {
			String name = authentication.getName();
			User user = userService.findByUsername(name);
			if (user != null)
				model.addAttribute("user", user);
		}

		return "page/index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		if(checkLogedIn()) {
			return "redirect:/";
		}
		model.addAttribute("user", new User());

		return "page/login";
	}

	@GetMapping("/register")
	public String registerForm(Model model) {
		if(checkLogedIn()) {
			return "redirect:/";
		}
		model.addAttribute("user", new UserRegistrationDto());

		return "page/register";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/dashboard")
	public String getAdminDashboard(Model model) {
		return "page/dashboard";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") UserRegistrationDto dto, BindingResult result, Model model) {
		User user = modelMapper.map(dto, User.class);
		user.setRole(roleService.findByName("ROLE_ADMIN"));
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.save(user);
		return "page/login";
	}

	private boolean checkLogedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
 
        return true;
	}
}
