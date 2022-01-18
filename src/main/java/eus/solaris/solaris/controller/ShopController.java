package eus.solaris.solaris.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ShopController {

	@GetMapping
	public String index(Model model, Authentication authentication) {
		return "page/shop-product";
	}
}
