package eus.solaris.solaris.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.exception.AvatarNotCreatedException;
import eus.solaris.solaris.form.UserRegistrationForm;
import eus.solaris.solaris.service.LanguageService;
import eus.solaris.solaris.service.RoleService;
import eus.solaris.solaris.service.UserService;

@Controller
public class AppController {

	@Autowired
	UserService userService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	RoleService roleService;

	@Autowired
	LanguageService languageService;

	@GetMapping("/")
	public String index(Model model, Authentication authentication) {
		if (authentication != null) {
			String name = authentication.getName();
			User user = userService.findByUsername(name);
			if (user != null)
				model.addAttribute("user", user);
		}

		return "page/index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		if (checkLogedIn()) {
			return "redirect:/";
		}
		model.addAttribute("user", new User());

		return "page/login";
	}

	@GetMapping("/apitest")
	public String apitest() {
		return "page/apitest";
	}

	@GetMapping("/register")
	public String registerForm(Model model) {
		if (checkLogedIn()) {
			return "redirect:/";
		}
		model.addAttribute("form", new UserRegistrationForm());

		return "page/register";
	}

	@PostMapping("/register")
	public String registerUser(@Validated @ModelAttribute UserRegistrationForm form, BindingResult result,
			Model model) throws AvatarNotCreatedException {
		if (result.hasErrors()
				|| form.getUsername() != null
						&& !form.getUsername().isBlank()
						&& userService.findByUsername(form.getUsername()) != null) {
			Locale locale = LocaleContextHolder.getLocale();
			List<ObjectError> errors = new ArrayList<>(result.getAllErrors());
			errors.add(new ObjectError("username duplicated",
					messageSource.getMessage("page.register.field.username.duplicated", null, locale)));
			model.addAttribute("errors", errors);
			model.addAttribute("form", form);
			return "page/register";
		}

		if (checkLogedIn()) {
			return "redirect:/";
		}

		userService.register(form);
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
