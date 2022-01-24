package eus.solaris.solaris.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.Brand;
import eus.solaris.solaris.domain.CartProduct;
import eus.solaris.solaris.domain.Color;
import eus.solaris.solaris.domain.Country;
import eus.solaris.solaris.domain.Material;
import eus.solaris.solaris.domain.Order;
import eus.solaris.solaris.domain.OrderProduct;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.domain.Size;
import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.CheckoutForm;
import eus.solaris.solaris.form.ProductFilterForm;
import eus.solaris.solaris.service.AddressService;
import eus.solaris.solaris.service.CountryService;
import eus.solaris.solaris.service.PaymentMethodService;
import eus.solaris.solaris.service.ProductService;
import eus.solaris.solaris.service.ProvinceService;
import eus.solaris.solaris.service.SolarPanelService;
import eus.solaris.solaris.service.UserService;

@Controller
@RequestMapping("/shop")
public class ShopController {

	private static final List<String> PAYMENT_FIELDS = Stream
			.of("cardNumber", "cardHolderName", "securityCode", "expirationYear", "expirationMonth")
			.collect(Collectors.toList());
	private static final List<String> ADDRESS_FIELDS = Stream
			.of("street", "countryId", "city", "postcode", "provinceId", "number").collect(Collectors.toList());

	@Autowired
	UserService userService;

	@Autowired
	ProductService productService;

	@Autowired
	AddressService addressService;

	@Autowired
	PaymentMethodService paymentMethodService;

	@Autowired
	CountryService countryService;

	@Autowired
	ProvinceService provinceService;

	@Autowired
	SolarPanelService solarPanelService;

	@GetMapping({ "", "/{page}" })
	public String shopIndex(@ModelAttribute ProductFilterForm pff, BindingResult result, Model model,
			Authentication authentication, @PathVariable(required = false) Integer page) {
		if (page == null)
			page = 0;
		Page<Product> products = productService.getFilteredProducts(pff, page);
		setFilters(model);
		model.addAttribute("actualPage", page);
		model.addAttribute("products", products.getContent());
		model.addAttribute("totalPages", products.getTotalPages());
		model.addAttribute("form", pff);

		return "page/shop-products";
	}

	@GetMapping("/checkout")
	public String displayCheckout(Model model, Authentication authentication) {
		User user = (User) model.getAttribute("user");
		List<CartProduct> cart;
		Double subtotal;
		if (user == null) {
			return "redirect:/shop";
		}

		cart = user.getShoppingCart();
		subtotal = cart.stream().mapToDouble(x -> x.getQuantity() * x.getProduct().getPrice()).sum();
		model.addAttribute("cart", cart);
		model.addAttribute("subtotal", subtotal);
		return "page/shop-checkout";
	}

	@PreAuthorize("hasAuthority('AUTH_LOGGED_READ')")
	@PostMapping("/checkout")
	public String confirmOrder(@Validated @ModelAttribute CheckoutForm form, BindingResult result, Model model) {
		User user = (User) model.getAttribute("user");
		Order order = new Order();
		Address address;
		PaymentMethod paymentMethod;

		List<ObjectError> errors;

		if (result.hasErrors()) {
			errors = filterErrors(form, result.getAllErrors());
			if (errors.size() > 0) {
				model.addAttribute("errors", errors	);
				return "page/shop-checkout";
			}
		}

		if (form.getAddressId() != null) {
			order.setAddress(addressService.findById(form.getAddressId()));
		} else {
			address = createAddress(form);
			address.setUser(user);
			addressService.save(address);
		}

		if (form.getPaymentMethodId() != null) {
			order.setPaymentMethod(paymentMethodService.findById(form.getPaymentMethodId()));
		} else {
			paymentMethod = createPaymentMethod(form);
			paymentMethod.setUser(user);
			paymentMethodService.save(paymentMethod);
		}

		purchaseProducts(form.getProducts(), order, user);

		return "page/index";
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

	private Address createAddress(CheckoutForm form) {
		Address a = new Address();
		Country country = countryService.findById(form.getCountryId());
		Province province = provinceService.findById(form.getProvinceId());

		a.setCountry(country);
		a.setProvince(province);
		a.setCity(form.getCity());
		a.setPostcode(form.getPostcode());
		a.setStreet(form.getStreet());
		a.setNumber(form.getNumber());
		a.setEnabled(form.isSaveAddress());

		return a;
	}

	private PaymentMethod createPaymentMethod(CheckoutForm form) {
		PaymentMethod p = new PaymentMethod();
		p.setCardHolderName(form.getCardHolderName());
		p.setCardNumber(form.getCardNumber());
		p.setExpirationMonth(form.getExpirationMonth());
		p.setExpirationYear(form.getExpirationYear());
		p.setSecurityCode(form.getSecurityCode());
		p.setDefaultMethod(form.isSavePaymentMethod());
		p.setDefaultMethod(form.isSavePaymentMethod());

		return p;
	}

	private void purchaseProducts(Map<Long, Integer> productMap, Order order, User user) {
		Product product;
		Set<OrderProduct> productLst = new HashSet<>();
		OrderProduct orderProduct;
		SolarPanel solarPanel;

		for (Long id : productMap.keySet()) {
			product = productService.findById(id);
			orderProduct = new OrderProduct();
			orderProduct.setProduct(product);
			orderProduct.setOrder(order);
			orderProduct.setAmount(productMap.get(id));
			orderProduct.setPrice(product.getPrice());

			productLst.add(orderProduct);

			solarPanel = new SolarPanel();
			solarPanel.setModel(product.getModel());
			solarPanel.setUser(user);
			solarPanel.setTimestamp(Instant.now());

			user.getSolarPanels().add(solarPanel);
		}
	}

	private List<ObjectError> filterErrors(CheckoutForm form, List<ObjectError> errors) {
		List<ObjectError> filteredErrors = new ArrayList<>(errors);
		ListIterator<ObjectError> iterator = filteredErrors.listIterator();
		while (iterator.hasNext()) {
			FieldError fError = (FieldError) iterator.next();
			if (form.getAddressId() != null) {
				if (ADDRESS_FIELDS.contains(fError.getField())) {
					iterator.remove();
					continue;
				}

			}
			if (form.getPaymentMethodId() != null) {
				if (PAYMENT_FIELDS.contains(fError.getField()))
					iterator.remove();
			}
		}
		return filteredErrors;
	}
}
