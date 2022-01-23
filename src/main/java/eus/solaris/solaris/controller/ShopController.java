package eus.solaris.solaris.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.solaris.solaris.domain.Brand;
import eus.solaris.solaris.domain.CartProduct;
import eus.solaris.solaris.domain.Color;
import eus.solaris.solaris.domain.Material;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.Size;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.ProductFilterForm;
import eus.solaris.solaris.service.ProductService;
import eus.solaris.solaris.service.UserService;

@Controller
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	UserService userService;

	@Autowired
	ProductService productService;

	@GetMapping({"", "/{page}"})
	public String shopIndex(@ModelAttribute ProductFilterForm pff, BindingResult result, Model model,
			Authentication authentication, @PathVariable(required = false) Integer page) {
		if(page == null) page = 0;
        Page<Product> products = productService.getFilteredProducts(pff, page);
        setFilters(model);
        model.addAttribute("actualPage", page);
        model.addAttribute("products", products.getContent());
        model.addAttribute("totalPages", products.getTotalPages());
		model.addAttribute("form", pff);

		return "page/shop-products";
	}

	@GetMapping("/checkout")
	public String cart(Model model, Authentication authentication) {
		User user = (User) model.getAttribute("user");
		List<CartProduct> cart;
		Double subtotal;
		if (user == null) {
			return "redirect:/shop";
		}

		cart = user.getShoppingCart();
		subtotal = cart.stream().mapToDouble(x -> x.getQuantity()*x.getProduct().getPrice()).sum();
		model.addAttribute("cart", cart);
		model.addAttribute("subtotal", subtotal);
		return "page/shop-checkout";
	}

	@GetMapping("/product/{id}")
	public String cart(Model model, @PathVariable(name = "id") Long productId, Authentication authentication) {
		Product product = productService.findById(productId);
		model.addAttribute("product", product);
		return "page/shop-product";
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
