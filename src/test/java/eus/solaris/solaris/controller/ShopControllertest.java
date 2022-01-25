package eus.solaris.solaris.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import eus.solaris.solaris.config.SpringWebAuxTestConfig;
import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.Comunidad;
import eus.solaris.solaris.domain.Country;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.security.CustomUserDetails;
import eus.solaris.solaris.service.impl.AddressServiceImpl;
import eus.solaris.solaris.service.impl.CartProductServiceImpl;
import eus.solaris.solaris.service.impl.CountryServiceImpl;
import eus.solaris.solaris.service.impl.InstallationServiceImpl;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.OrderProductServiceImpl;
import eus.solaris.solaris.service.impl.OrderServiceImpl;
import eus.solaris.solaris.service.impl.PaymentMethodServiceImpl;
import eus.solaris.solaris.service.impl.ProductServiceImpl;
import eus.solaris.solaris.service.impl.ProvinceServiceImpl;
import eus.solaris.solaris.service.impl.RoleServiceImpl;
import eus.solaris.solaris.service.impl.SolarPanelServiceImpl;
import eus.solaris.solaris.service.impl.TaskServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@Import(SpringWebAuxTestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest({ ShopController.class, UserControllerAdvice.class })
public class ShopControllertest {
  
	@Autowired
	MockMvc mvc;

  @MockBean
	ProductServiceImpl productService;

	@MockBean
	OrderServiceImpl orderService;

	@MockBean
	OrderProductServiceImpl orderProductService;

	@MockBean
	AddressServiceImpl addressService;

	@MockBean
	PaymentMethodServiceImpl paymentMethodService;

	@MockBean
	CountryServiceImpl countryService;

	@MockBean
	ProvinceServiceImpl provinceService;

	@MockBean
	SolarPanelServiceImpl solarPanelService;

	@MockBean
	RoleServiceImpl roleService;

	@MockBean
	TaskServiceImpl taskService;

	@MockBean
	InstallationServiceImpl installationService;

	@MockBean
	CartProductServiceImpl cartProductService;

	@MockBean
	LanguageServiceImpl languageService;

	@MockBean
	UserServiceImpl userService;

	@MockBean
  PasswordEncoder passwordEncoder;

	@Autowired
	UserDetailsService userDetailsService;

  private User user;
  private String username;

  @BeforeEach
  void setup() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      username = authentication.getName();
      user = ((CustomUserDetails) userDetailsService.loadUserByUsername(username)).getuser();
      when(userService.findByUsername(username)).thenReturn(user);
    }
  }

	@Test
	@WithUserDetails("testyUser")
	void confirmOrderFormWithErrorsInAddress() throws Exception {
		mvc.perform(post("https://localhost/shop/checkout")
		.param("paymentMethodId", "1")
		.with(csrf()))
		.andExpect(status().isFound());
	}

	@Test
	@WithUserDetails("testyUser")
	void confirmOrderFormWithErrorsInPayment() throws Exception {
		mvc.perform(post("https://localhost/shop/checkout")
		.param("addressId", "1")
		.with(csrf()))
		.andExpect(status().isFound());
	}

	@Test
	@WithUserDetails("testyUser")
	void confirmOrderFormWithErrorsProduct() throws Exception {
		mvc.perform(post("https://localhost/shop/checkout")
		.param("paymentMethodId", "1")
		.param("addressId", "1")
		.with(csrf()))
		.andExpect(status().isFound());
	}

	@Test
	@WithUserDetails("testyUser")
	void confirmOrderFormWithAddressFilledManually() throws Exception {
		
		PaymentMethod p = createPaymentMethod();

		Country country = createCountry();
		Province province = createProvince();
		Address testAddress = createAddress();
		Address empty = createAddress();
		empty.setId(null);

		when(paymentMethodService.findById(p.getId())).thenReturn(p);
		when(countryService.findById(country.getId())).thenReturn(country);
		when(provinceService.findById(province.getId())).thenReturn(province);
		when(addressService.save(empty)).thenReturn(testAddress);

		mvc.perform(post("https://localhost/shop/checkout")
		.param("paymentMethodId", ""+p.getId())
		.param("street", empty.getStreet())
		.param("countryId", ""+country.getId())
		.param("city", empty.getCity())
		.param("postcode", empty.getPostcode())
		.param("provinceId", ""+province.getId())
		.param("number", empty.getNumber())
		.param("saveAddress", "false")
		.param("products[0]", "1")
		.with(csrf()))
		.andExpect(status().isBadRequest());
	}

	private PaymentMethod createPaymentMethod() {
		PaymentMethod p = new PaymentMethod();
		p.setId(1L);
		p.setCardHolderName("test");
		p.setCardNumber("1234567891234567");
		p.setDefaultMethod(false);
		p.setEnabled(true);
		p.setExpirationMonth(1L);
		p.setExpirationYear(2022L);
		p.setSecurityCode("123");
		p.setUser(new User());
		p.setVersion(0);

		return p;
	}

	private Country createCountry() {
		Country c = new Country();

		c.setId(1L);
		c.setI18n("test");
		c.setCode("Test");
		c.setVersion(0);

		return c;
	}

	private Province createProvince() {
		Province p = new Province();

		p.setId(1L);
		p.setI18n("araba");
		p.setCode("Araba");
		p.setCapitalProvincia("Gasteiz");
		p.setComunidad(new Comunidad());
		p.setVersion(0);

		return p;
	}

	private Address createAddress() {
		Address a = new Address();

		a.setId(1L);
		a.setCity("gasteiz");
		a.setCountry(createCountry());
		a.setDefaultAddress(false);
		a.setEnabled(false);
		a.setNumber("4");
		a.setPostcode("01210");
		a.setProvince(createProvince());
		a.setStreet("portal de gamarra");
		a.setUser(user);
		a.setVersion(0);

		return a;
	}

}
