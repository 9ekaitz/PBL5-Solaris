package eus.solaris.solaris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.UserService;

@Controller
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	UserService userService;

	@GetMapping
	public String shopIndex(Model model, Authentication authentication) {
		return "page/shop-product";
	}

	@GetMapping("/cart")
	public String cart(Model model, Authentication authentication) {
		if (authentication == null)
			return "page/login";
		User user = userService.findByUsername(authentication.getName());
		if (user == null) {
			return "redirect:/shop";
		}
		return "page/shop-cart";
	}
}
