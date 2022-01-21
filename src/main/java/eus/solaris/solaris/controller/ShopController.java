package eus.solaris.solaris.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.solaris.solaris.domain.Brand;
import eus.solaris.solaris.domain.Color;
import eus.solaris.solaris.domain.Material;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.Size;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.ProductService;
import eus.solaris.solaris.service.UserService;

@Controller
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	UserService userService;

	@Autowired
    ProductService productService;

	@GetMapping
	public String shopIndex(Model model, Authentication authentication) {
		setFilters(model);
        List<Product> products = productService.findAll();
        PagedListHolder<Product> pagedListHolder = productService.getPagesFromProductList(products);
        model.addAttribute("actualPage", 0);
        model.addAttribute("products", pagedListHolder.getPageList());
        model.addAttribute("totalPages", pagedListHolder.getPageCount());
		return "page/shop-products";
	}

	@GetMapping("/cart")
	public String cart(Model model, Authentication authentication) {
		User user = (User) model.getAttribute("user");
		if (user == null) {
			return "redirect:/shop";
		}
		user.getShoppingCart();
		return "page/shop-cart";
	}

	private void setFilters(Model model) {
        List<Brand> brands = productService.getBrands();
        model.addAttribute("brands", brands);
        List<Color> colors = productService.getColors();
        model.addAttribute("colors", colors);
        List<Size> sizes = productService.getSizes();
        model.addAttribute("sizes", sizes);
        List<Material> materials = productService.getMaterials();
        model.addAttribute("materials", materials);
    }

}
