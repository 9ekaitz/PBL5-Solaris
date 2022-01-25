package eus.solaris.solaris.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.Brand;
import eus.solaris.solaris.domain.CartProduct;
import eus.solaris.solaris.domain.Color;
import eus.solaris.solaris.domain.Country;
import eus.solaris.solaris.domain.Installation;
import eus.solaris.solaris.domain.Material;
import eus.solaris.solaris.domain.Order;
import eus.solaris.solaris.domain.OrderProduct;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.domain.Size;
import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.Task;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.CheckoutForm;
import eus.solaris.solaris.form.ProductFilterForm;
import eus.solaris.solaris.service.AddressService;
import eus.solaris.solaris.service.CartProductService;
import eus.solaris.solaris.service.CountryService;
import eus.solaris.solaris.service.InstallationService;
import eus.solaris.solaris.service.OrderProductService;
import eus.solaris.solaris.service.OrderService;
import eus.solaris.solaris.service.PaymentMethodService;
import eus.solaris.solaris.service.ProductService;
import eus.solaris.solaris.service.ProvinceService;
import eus.solaris.solaris.service.RoleService;
import eus.solaris.solaris.service.SolarPanelService;
import eus.solaris.solaris.service.TaskService;
import eus.solaris.solaris.service.UserService;

@Controller
@RequestMapping("/shop")
public class ShopController {

	private static final List<String> PAYMENT_FIELDS = Stream
			.of("cardNumber", "cardHolderName", "securityCode", "expirationYear", "expirationMonth")
			.collect(Collectors.toList());
	private static final List<String> ADDRESS_FIELDS = Stream
			.of("street", "countryId", "city", "postcode", "provinceId", "number").collect(Collectors.toList());

	private static final String TECHNICIAN_ROLE = "ROLE_TECHNICIAN";

	private static final String PROVINCE_ATTRIBUTE = "provinces";
	private static final String COUNTRY_ATTRIBUTE = "countries";
	private static final String REDIRECT_CHECKOUT = "redirect:/shop/checkout";
	private static final String REDIRECT_SHOP = "redirect:/shop";

	private static final Double INSTALLATION_PRICE = 200.0;

	@Autowired
	UserService userService;

	@Autowired
	ProductService productService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderProductService orderProductService;

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

	@Autowired
	RoleService roleService;

	@Autowired
	TaskService taskService;

	@Autowired
	InstallationService installationService;

	@Autowired
	CartProductService cartProductService;

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
			return REDIRECT_SHOP;
		}
		CheckoutForm form = new CheckoutForm();
		cart = user.getShoppingCart();
		subtotal = cart.stream().mapToDouble(x -> x.getQuantity() * x.getProduct().getPrice()).sum();
		model.addAttribute("cart", cart);
		model.addAttribute("subtotal", subtotal);
		model.addAttribute("installationPrice", INSTALLATION_PRICE);
		model.addAttribute("form", form);
		addAddressAttributes(model, user);
		return "page/shop-checkout";
	}

	@PreAuthorize("hasAuthority('AUTH_LOGGED_READ')")
	@PostMapping("/checkout")
	public String confirmOrder(@Validated @ModelAttribute CheckoutForm form, BindingResult result, Model model,
			RedirectAttributes rAttributes) {
		User user = (User) model.getAttribute("user");
		Order order = new Order();
		Address address;
		PaymentMethod paymentMethod;
		List<ObjectError> errors;

		if (result.hasErrors()) {
			errors = filterErrors(form, result.getAllErrors());
			if (!errors.isEmpty()) {
				rAttributes.addFlashAttribute("errors", errors);
				return REDIRECT_CHECKOUT;
			}
		}

		order.setOwner(user);
		if (form.getAddressId() != null) {
			order.setAddress(addressService.findById(form.getAddressId()));
		} else {
			address = createAddress(form);
			address.setUser(user);
			order.setAddress(addressService.save(address));
		}

		if (form.getPaymentMethodId() != null) {
			order.setPaymentMethod(paymentMethodService.findById(form.getPaymentMethodId()));
		} else {
			paymentMethod = createPaymentMethod(form);
			paymentMethod.setUser(user);
			order.setPaymentMethod(paymentMethodService.save(paymentMethod));
		}
		order = orderService.save(order);
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
		p.setEnabled(form.isSavePaymentMethod());

		return p;
	}

	private void purchaseProducts(List<Long> products, Order order, User user) {
		Product product;
		Set<OrderProduct> productLst = new HashSet<>();
		CartProduct p;

		for (Long id : products) {
			if ((p = cartProductService.findById(id)) == null)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The cart product doesn't exist");
			
			product = p.getProduct();
			productLst.add(orderProductService.create(order, product, p.getQuantity()));
			cartProductService.delete(p);
			user.getShoppingCart().remove(p);

			for (int i = 0; i < p.getQuantity(); i++) {
				createSolarPanel(product, order, user);
			}
		}
		createInstallation(order);
		order.setProducts(productLst);
		order.setInstallationCost(INSTALLATION_PRICE);
		orderService.save(order);
	}

	private void createSolarPanel(Product product, Order order, User user) {
		SolarPanel p = solarPanelService.create(product.getModel(), user, order.getAddress().getProvince());
		user.getSolarPanels().add(p);
	}

	private void createInstallation(Order order) {
		Installation installation = new Installation();
		List<User> installers = roleService.findByName(TECHNICIAN_ROLE).getUsers();
		Random r = new Random(System.currentTimeMillis());
		int mod = installers.size();
		User installer;
		if (mod == 0)
			installer = null;
		else
			installer = installers.get(r.nextInt() % mod);

		installation.setOrder(order);
		installation.setName("Install panel in " + order.getAddress().getCity() + ", " + order.getAddress().getStreet());
		installation
				.setDescription("Install panel in " + order.getAddress().getCity() + ", " + order.getAddress().getStreet());
		installation.setInstaller(installer);
		installation = installationService.save(installation);
		installation.setTasks(createTasks(installation));

		order.setInstallation(installationService.save(installation));
	}

	private List<Task> createTasks(Installation installation) {
		Task t1 = new Task();
		Task t2 = new Task();
		Task t3 = new Task();

		t1.setDescription("Install the wires for the panels");
		t1.setInstallation(installation);
		t2.setDescription("Install the panels");
		t2.setInstallation(installation);
		t3.setDescription("Connect the panels to the power grid");
		t3.setInstallation(installation);

		return Stream.of(taskService.save(t1), taskService.save(t2), taskService.save(t3)).collect(Collectors.toList());
	}

	private List<ObjectError> filterErrors(CheckoutForm form, List<ObjectError> errors) {
		List<ObjectError> filteredErrors = new ArrayList<>(errors);
		ListIterator<ObjectError> iterator = filteredErrors.listIterator();
		while (iterator.hasNext()) {
			FieldError fError = (FieldError) iterator.next();
			if (form.getAddressId() != null && ADDRESS_FIELDS.contains(fError.getField())) {
				iterator.remove();
				continue;
			}

			if (form.getPaymentMethodId() != null && PAYMENT_FIELDS.contains(fError.getField()))
				iterator.remove();
		}
		return filteredErrors;
	}

	private void addAddressAttributes(Model model, User user) {
		model.addAttribute(PROVINCE_ATTRIBUTE, provinceService.findAll());
		model.addAttribute(COUNTRY_ATTRIBUTE, countryService.findAll());
		model.addAttribute("addresses",
				user.getAddresses().stream().filter(Address::isEnabled).collect(Collectors.toList()));
		model.addAttribute("paymentMethods",
				user.getPaymentMethods().stream().filter(PaymentMethod::isEnabled).collect(Collectors.toList()));
	}
}
