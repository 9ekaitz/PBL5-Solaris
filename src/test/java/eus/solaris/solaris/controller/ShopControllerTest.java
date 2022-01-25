package eus.solaris.solaris.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import eus.solaris.solaris.config.SpringWebAuxTestConfig;
import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.Brand;
import eus.solaris.solaris.domain.CartProduct;
import eus.solaris.solaris.domain.Color;
import eus.solaris.solaris.domain.Comunidad;
import eus.solaris.solaris.domain.Country;
import eus.solaris.solaris.domain.Material;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.Privilege;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.Size;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.ProductFilterForm;
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
public class ShopControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  UserServiceImpl userService;

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
  PasswordEncoder passwordEncoder;

  @Autowired
	UserDetailsService userDetailsService;

  private User user;
  private String username;

  User basicUser;

  @BeforeEach
  void loadUser() {
    String username = "testyUser";
    Privilege privilege = createUserLoggedPrivilege();

    Role ROLE_USER = new Role(1L, "ROLE_USER", true, null, Stream
        .of(privilege)
        .collect(Collectors.toSet()), 1);

    List<Address> addresses = Stream
        .of(new Address(1L, new Country(1L, null, "uwu", 1), new Province(1L, null, "UWU", null, null, 1),
            "Vitoria", "01008", "Pintor Clemente Arraiz",
            "680728473", basicUser, true, true, 1))
        .collect(Collectors.toList());

    List<PaymentMethod> paymentMethods = Stream
        .of(new PaymentMethod(1L, basicUser, "Aritz Domaika Peirats", "5555666677778888", 1L, 2027L, "222",
            true, true, 1),
            new PaymentMethod(2L, basicUser, "Aritz Peirats Domaika", "5555666677778888", 2L, 2024L, "1",
                false, true, 1))
        .collect(Collectors.toList());

    basicUser = createUser(addresses, paymentMethods, ROLE_USER);

    when(userService.findByUsername(username)).thenReturn(basicUser);
  }

  @Test
  @WithUserDetails(value = "testyUser")
  void getShopIndexTest() throws Exception {
    List<Product> products = Stream
        .of(new Product(1L, 200D, null, null, null, 1), new Product(2L, 300D, null, null, null, 1),
            new Product(3L, 400D, null, null, null, 1))
        .collect(Collectors.toList());
    Page<Product> page = new PageImpl<>(products);
    List<Long> ids = Stream.of(1L).collect(Collectors.toList());

    ProductFilterForm productFilterForm = new ProductFilterForm();
    productFilterForm.setBrandsIds(ids);
    productFilterForm.setColorsIds(ids);
    productFilterForm.setMaterialsIds(ids);
    productFilterForm.setSizesIds(ids);

    when(productService.getFilteredProducts(any(), anyInt())).thenReturn(page);
    when(productService.getBrands()).thenReturn(Stream.of(new Brand()).collect(Collectors.toList()));
    when(productService.getColors()).thenReturn(Stream.of(new Color()).collect(Collectors.toList()));
    when(productService.getMaterials()).thenReturn(Stream.of(new Material()).collect(Collectors.toList()));
    when(productService.getSizes()).thenReturn(Stream.of(new Size()).collect(Collectors.toList()));

    mockMvc.perform(get("https://localhost/shop")
        .param("brandsIds[0]", ids.get(0).toString())
        .param("colorsIds[0]", ids.get(0).toString())
        .param("sizesIds[0]", ids.get(0).toString())
        .param("materialsIds[0]", ids.get(0).toString()))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("brands"))
        .andExpect(model().attributeExists("colors"))
        .andExpect(model().attributeExists("sizes"))
        .andExpect(model().attributeExists("materials"))
        .andExpect(model().attributeExists("actualPage"))
        .andExpect(model().attributeExists("products"))
        .andExpect(model().attributeExists("totalPages"))
        .andExpect(model().attributeExists("form"))
        .andExpect(view().name("page/shop-products"));
  }

  @Test
  @WithUserDetails(value = "testyUser")
  void getCheckoutTestShoppingCartEmpty() throws Exception {

    mockMvc.perform(get("https://localhost/shop/checkout"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/shop"));
  }

  @Test
  @WithUserDetails(value = "testyUser")
  void getCheckoutTestShoppingNull() throws Exception {

    basicUser.setShoppingCart(null);

    mockMvc.perform(get("https://localhost/shop/checkout"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/shop"));
  }

  @Test
  @WithUserDetails(value = "testyUser")
  void getCheckoutTest() throws Exception {
    List<CartProduct> cartProducts = Stream
        .of(new CartProduct(1L, new Product(1L, 200D, null, null, null, 1), 2, basicUser, 1),
            new CartProduct(2L, new Product(2L, 150D, null, null, null, 1), 1, basicUser, 1))
        .collect(Collectors.toList());

    basicUser.setShoppingCart(cartProducts);

    mockMvc.perform(get("https://localhost/shop/checkout"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("cart"))
        .andExpect(model().attributeExists("subtotal"))
        .andExpect(model().attributeExists("installationPrice"))
        .andExpect(model().attributeExists("form"))
        .andExpect(model().attributeExists("provinces"))
        .andExpect(model().attributeExists("countries"))
        .andExpect(model().attributeExists("addresses"))
        .andExpect(model().attributeExists("paymentMethods"))
        .andExpect(view().name("page/shop-checkout"));
  }

  @Test
  @WithUserDetails(value = "testyUser")
  void getCartTest() throws Exception {
    Product product = new Product(1L, 200D, null, null, null, 1);
    when(productService.findById(1L)).thenReturn(product);

    mockMvc.perform(get("https://localhost/shop/product/1"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("product"))
        .andExpect(view().name("page/shop-product"));
  }

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
    mockMvc.perform(post("https://localhost/shop/checkout")
        .param("paymentMethodId", "1")
        .with(csrf()))
        .andExpect(status().isFound());
  }

  @Test
  @WithUserDetails("testyUser")
  void confirmOrderFormWithErrorsInPayment() throws Exception {
    mockMvc.perform(post("https://localhost/shop/checkout")
        .param("addressId", "1")
        .with(csrf()))
        .andExpect(status().isFound());
  }

  @Test
  @WithUserDetails("testyUser")
  void confirmOrderFormWithErrorsProduct() throws Exception {
    mockMvc.perform(post("https://localhost/shop/checkout")
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

    mockMvc.perform(post("https://localhost/shop/checkout")
        .param("paymentMethodId", "" + p.getId())
        .param("street", empty.getStreet())
        .param("countryId", "" + country.getId())
        .param("city", empty.getCity())
        .param("postcode", empty.getPostcode())
        .param("provinceId", "" + province.getId())
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

  private Privilege createUserLoggedPrivilege() {
    Privilege privilege = new Privilege();
    privilege.setId(1L);
    privilege.setCode("USER_LOGGED_VIEW");
    privilege.setI18n("user.logged.view");
    privilege.setEnabled(true);
    privilege.setVersion(1);
    return privilege;
  }

  private User createUser(List<Address> addresses, List<PaymentMethod> paymentMethods, Role ROLE_USER) {
    return new User(1L, "testyUser", "testy@foo", "foo123", "Testy", "Tester", "User", true, addresses,
        paymentMethods, ROLE_USER, null, new ArrayList<>(), null, 1);
  }
}
