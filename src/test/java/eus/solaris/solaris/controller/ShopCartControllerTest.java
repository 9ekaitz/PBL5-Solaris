package eus.solaris.solaris.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import eus.solaris.solaris.config.SpringWebAuxTestConfig;
import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.CartProduct;
import eus.solaris.solaris.domain.Country;
import eus.solaris.solaris.domain.Language;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.Privilege;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.ProductDescription;
import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.dto.ProductDto;
import eus.solaris.solaris.dto.ShopCartOutputDto;
import eus.solaris.solaris.repository.CartProductRepository;
import eus.solaris.solaris.repository.ProductRepository;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.ShopServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@Import(SpringWebAuxTestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest({ ShopCartController.class, UserControllerAdvice.class })
class ShopCartControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    ShopServiceImpl shopService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    CartProductRepository cartProductRepository;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    ModelMapper modelMapper;

    @MockBean
    LanguageServiceImpl languageService; 

    @MockBean
    PasswordEncoder passwordEncoder;

    User basicUser;

    Product product;

    ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  void loadUser() {
    String username = "testyUser";
    Privilege privilege = createUserLoggedPrivilege();

    Role ROLE_USER = new Role(1L, "ROLE_USER", true, null, "role.user", Stream
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



    Set<ProductDescription> productDescriptions = Stream
        .of(new ProductDescription(1L, new Language(1L, "en", "en", 1), "Name", "Subtitle", "Description", product, 1)).collect(Collectors.toSet());

    product = new Product(1L, 200D, null, productDescriptions, null, 1);


    List<CartProduct> cartProducts = Stream
        .of(new CartProduct(1L, product, 2, basicUser, 1))
        .collect(Collectors.toList());


    basicUser = createUser(addresses, paymentMethods, cartProducts, ROLE_USER);

    when(userService.findByUsername(username)).thenReturn(basicUser);
  }

  @Test
  @WithUserDetails(value = "testyUser")
  void testGetCart() throws Exception {
    ShopCartOutputDto shopCartOutputDto = new ShopCartOutputDto();

    ProductDto productDto = new ProductDto();
    productDto.setName("uwu");
    productDto.setTotalPrice(400D);

    shopCartOutputDto.setProducts(Stream.of(productDto).collect(Collectors.toList()));
    shopCartOutputDto.setTotalPrice(400D);


    when(modelMapper.map(basicUser.getShoppingCart().get(0), ProductDto.class)).thenReturn(productDto);
    try (MockedStatic<LocaleContextHolder> utilities = Mockito.mockStatic(LocaleContextHolder.class)) {
        utilities.when(() -> LocaleContextHolder.getLocale()).thenReturn(Locale.ENGLISH);

        ResultActions result = mockMvc.perform(get("https://localhost/shop-cart"))
        .andExpect(status().isOk());

        String json = mapper.writeValueAsString(shopCartOutputDto);
        assertEquals(json, result.andReturn().getResponse().getContentAsString());
    }
  }

//   @Test
//   @WithUserDetails(value = "testyUser")
//   void postAddProduct() throws Exception {
//     ShopCartOutputDto shopCartOutputDto = new ShopCartOutputDto();

//     ProductDto productDto = new ProductDto();
//     productDto.setName("uwu");
//     productDto.setTotalPrice(400D);

//     shopCartOutputDto.setProducts(Stream.of(productDto).collect(Collectors.toList()));
//     shopCartOutputDto.setTotalPrice(400D);

//     when(modelMapper.map(basicUser.getShoppingCart().get(0), ProductDto.class)).thenReturn(productDto);
//     try (MockedStatic<LocaleContextHolder> utilities = Mockito.mockStatic(LocaleContextHolder.class)) {
//         utilities.when(() -> LocaleContextHolder.getLocale()).thenReturn(Locale.ENGLISH);

//         ResultActions result = mockMvc.perform(post("https://localhost/shop-cart/add")
//         .with(csrf()))
//         .andExpect(status().isOk());

//         String json = mapper.writeValueAsString(shopCartOutputDto);
//         assertEquals(json, result.andReturn().getResponse().getContentAsString());
//     }
//   }

  private Privilege createUserLoggedPrivilege() {
    Privilege privilege = new Privilege();
    privilege.setId(1L);
    privilege.setCode("AUTH_LOGGED_VIEW");
    privilege.setI18n("auth.logged.view");
    privilege.setEnabled(true);
    privilege.setVersion(1);
    return privilege;
  }

  private User createUser(List<Address> addresses, List<PaymentMethod> paymentMethods, List<CartProduct> cartProducts, Role ROLE_USER) {
    return new User(1L, "testyUser", "testy@foo", "foo123", "Testy", "Tester", "User", true, addresses,
        paymentMethods, ROLE_USER, null, cartProducts, null, 1);
  }
    
}
