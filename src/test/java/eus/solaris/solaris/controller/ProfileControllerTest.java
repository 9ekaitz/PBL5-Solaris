package eus.solaris.solaris.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
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
import eus.solaris.solaris.domain.Privilege;
import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserInformationEditForm;
import eus.solaris.solaris.repository.DataEntryRepository;
import eus.solaris.solaris.repository.SolarPanelRepository;
import eus.solaris.solaris.service.impl.AddressServiceImpl;
import eus.solaris.solaris.service.impl.CountryServiceImpl;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.PaymentMethodServiceImpl;
import eus.solaris.solaris.service.impl.ProvinceServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@Import(SpringWebAuxTestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest({ ProfileController.class, UserControllerAdvice.class })
class ProfileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userServiceImpl;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    CountryServiceImpl countryServiceImpl;

    @MockBean
    ProvinceServiceImpl provinceServiceImpl;

    @MockBean
    AddressServiceImpl addressServiceImpl;

    @MockBean
    PaymentMethodServiceImpl paymentMethodServiceImpl;

    @MockBean
    SolarPanelRepository solarPanelRespository;

    @MockBean
    DataEntryRepository dataEntryRepository;

    @MockBean
    LanguageServiceImpl languageServiceImpl;

    @Autowired
    UserDetailsService userDetailsService;

    Authentication authentication;
    User basicUser;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void loadUser() {
        String username = "testyUser";
        Privilege privilege = createUserLoggedPrivilege();

        Role ROLE_USER = new Role(1L, "ROLE_USER", true, null, "role.user", Stream
                .of(privilege)
                .collect(Collectors.toSet()), 1);

        List<Address> addresses = Stream
                .of(new Address(1L, new Country(), new Province(), "Vitoria", "01008", "Pintor Clemente Arraiz",
                        "680728473", basicUser, true, true, 1),
                        new Address(2L, null, null, null, null, null, null, basicUser, true, false, 1))
                .collect(Collectors.toList());

        List<PaymentMethod> paymentMethods = Stream
                .of(new PaymentMethod(1L, basicUser, "Aritz Domaika Peirats", "5555666677778888", 1L, 2027L, "222",
                        true, true, 1),
                        new PaymentMethod(2L, basicUser, "Aritz Peirats Domaika", "5555666677778888", 2L, 2024L, "1",
                                false, true, 1))
                .collect(Collectors.toList());

        basicUser = createUser(addresses, paymentMethods, ROLE_USER);

        when(userServiceImpl.findByUsername(username)).thenReturn(basicUser);
    }

    private User createUser(List<Address> addresses, List<PaymentMethod> paymentMethods, Role ROLE_USER) {
        return new User(1L, "testyUser", "testy@foo", "foo123", "Testy", "Tester", "User", true, addresses,
                paymentMethods, ROLE_USER, null, null, null, 1);
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void getProfileTest() throws Exception {

        mockMvc.perform(get("https://localhost/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("page/profile"))
                .andExpect(model().attribute("user", basicUser));
    }

    @Test
    void getProfileNotLoggedTest() throws Exception {

        mockMvc.perform(get("https://localhost/profile"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void getProfileSecurityTest() throws Exception {

        mockMvc.perform(get("https://localhost/profile/security"))
                .andExpect(status().isOk())
                .andExpect(view().name("page/profile_security"))
                .andExpect(model().attributeExists("form"))
                .andExpect(model().attribute("user", basicUser));
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileSecurityTestTrue() throws Exception {
        String oldPassword = createOldPassword();
        String verifyNewPasword = createVerifyNewPassword();
        User basicUserModify = createUser(null, null, null);
        basicUserModify.setPassword(verifyNewPasword);

        when(userServiceImpl.editPassword(verifyNewPasword, oldPassword, basicUser)).thenReturn(basicUserModify);
        when(passwordEncoder.encode(verifyNewPasword)).thenReturn(verifyNewPasword);

        mockMvc.perform(post("https://localhost/profile/security")
                .with(csrf())
                .param("oldPassword", oldPassword)
                .param("verifyNewPasword", verifyNewPasword))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/profile"));

    }

    private String createVerifyNewPassword() {
        return "bar123";
    }

    private String createOldPassword() {
        return "foo123";
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileSecurityTestFalse() throws Exception {
        String oldPassword = createOldPassword();
        String verifyNewPasword = createVerifyNewPassword();

        when(userServiceImpl.editPassword(verifyNewPasword, oldPassword, basicUser)).thenReturn(basicUser);
        when(passwordEncoder.encode(verifyNewPasword)).thenReturn(verifyNewPasword);

        mockMvc.perform(post("https://localhost/profile/security")
                .with(csrf())
                .param("oldPassword", oldPassword)
                .param("verifyNewPasword", verifyNewPasword))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"))
                .andExpect(view().name("redirect:/profile"));

    }

    @Test
    @WithUserDetails(value = "testyUser")
    void getProfileDeleteAccountTest() throws Exception {

        mockMvc.perform(get("https://localhost/profile/delete-account"))
                .andExpect(status().isOk())
                .andExpect(view().name("page/delete_account"))
                .andExpect(model().attribute("user", basicUser));

    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileDeleteAccountTestSuccess() throws Exception {
        User basicUserModify = createUser(null, null, null);
        basicUserModify.setEnabled(false);

        when(userServiceImpl.disableUser(basicUser)).thenReturn(basicUserModify);

        mockMvc.perform(post("https://localhost/profile/delete-account")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/logout"));

    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileDeleteAccountTestError() throws Exception {

        User basicUserModify = createUser(null, null, null);

        when(userServiceImpl.disableUser(basicUser)).thenReturn(basicUserModify);

        mockMvc.perform(post("https://localhost/profile/delete-account")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"))
                .andExpect(view().name("redirect:/logout"));

    }

    @Test
    @WithUserDetails(value = "testyUser")
    void getProfileEditTest() throws Exception {

        mockMvc.perform(get("https://localhost/profile/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("page/profile_edit"));
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileEditTestSuccess() throws Exception {
        String name = createUsersName();
        String firstSurname = createUsersFirstSurname();
        String secondSurname = createUsersSecondSurname();
        String email = createUsersEmail();
        UserInformationEditForm userInformationEditForm = new UserInformationEditForm(name, firstSurname, secondSurname,
                email);
        User basicUserModify = createUser(null, null, null);
        basicUserModify.setName(name);
        basicUserModify.setFirstSurname(firstSurname);
        basicUserModify.setSecondSurname(secondSurname);
        basicUserModify.setEmail(email);

        when(userServiceImpl.editUser(userInformationEditForm, basicUser)).thenReturn(basicUserModify);

        mockMvc.perform(post("https://localhost/profile/edit")
                .with(csrf())
                .param("name", name)
                .param("firstSurname", firstSurname)
                .param("secondSurname", secondSurname)
                .param("email", email))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/profile"));

    }

    private String createUsersEmail() {
        return "testy@foo.com";
    }

    private String createUsersSecondSurname() {
        return "tester";
    }

    private String createUsersFirstSurname() {
        return "testy";
    }

    private String createUsersName() {
        return "testyUser";
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileEditTestError() throws Exception {
        String name = createUsersName();
        String firstSurname = createUsersFirstSurname();
        String secondSurname = createUsersSecondSurname();
        String email = createUsersEmail();
        UserInformationEditForm userInformationEditForm = new UserInformationEditForm(name, firstSurname, secondSurname,
                email);

        when(userServiceImpl.editUser(userInformationEditForm, basicUser)).thenReturn(null);

        mockMvc.perform(post("https://localhost/profile/edit")
                .with(csrf())
                .param("name", name)
                .param("firstSurname", firstSurname)
                .param("secondSurname", secondSurname)
                .param("email", email))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"))
                .andExpect(view().name("redirect:/profile"));

    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileEditTestFormFailed() throws Exception {
        String name = createUsersName();
        String firstSurname = createUsersFirstSurname();
        String secondSurname = createUsersSecondSurname();
        String email = createUsersName();
        UserInformationEditForm userInformationEditForm = new UserInformationEditForm(name, firstSurname, secondSurname,
                email);

        User basicUserModify = new User(1L, "testyUser", "testy", "foo123", "testyUser", "testy", "tester", true, null,
                null, null, null, null, null, 1);

        when(userServiceImpl.editUser(userInformationEditForm, basicUser)).thenReturn(basicUserModify);

        mockMvc.perform(post("https://localhost/profile/edit")
                .with(csrf())
                .param("name", name)
                .param("firstSurname", firstSurname)
                .param("secondSurname", secondSurname)
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attributeExists("form"))
                .andExpect(view().name("page/profile_edit"));

    }

    // @Test
    // @WithUserDetails(value="testyUser")
    // void getProfileAddressTest() throws Exception {

    // when(userServiceImpl.getUserAddresses(basicUser)).thenReturn(basicUser.getAddresses());

    // mockMvc.perform(get("https://localhost/profile/address"))
    // .andExpect(status().isOk())
    // .andExpect(model().attributeExists("addresses"))
    // .andExpect(view().name("page/profile_address"));
    // }

    @Test
    @WithUserDetails(value = "testyUser")
    void getProfileAddressAddTest() throws Exception {

        mockMvc.perform(get("https://localhost/profile/address/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("page/profile_address_edit"))
                .andExpect(model().attributeExists("provinces"))
                .andExpect(model().attributeExists("countries"))
                .andExpect(model().attribute("user", basicUser));
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileAddressAddTestSuccess() throws Exception {
        String street = createAddressStreet();
        Long countryId = createAddressCountryId();
        String city = createAddressCity();
        String postcode = createAddressPostcode();
        Long provinceId = createAddressProvinceId();
        String number = createAddressNumber();
        Country country = createCountry(countryId);
        Province province = createProvince(provinceId);

        Address address = createAddress(street, country, city, postcode, province, number);
        Address addressChanged = createAddress(street, country, city, postcode, province, number);
        addressChanged.setId(1L);
        addressChanged.setVersion(1);

        when(countryServiceImpl.findById(countryId)).thenReturn(country);
        when(provinceServiceImpl.findById(provinceId)).thenReturn(province);
        when(addressServiceImpl.save(address)).thenReturn(addressChanged);

        mockMvc.perform(post("https://localhost/profile/address/add")
                .with(csrf())
                .param("street", street)
                .param("countryId", countryId.toString())
                .param("city", city)
                .param("postcode", postcode)
                .param("provinceId", provinceId.toString())
                .param("number", number))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/profile/address"));

    }

    private Address createAddress(String street, Country country, String city, String postcode, Province province,
            String number) {
        return new Address(null, country, province, city, postcode, street, number, basicUser, true, true, null);
    }

    private Province createProvince(Long provinceId) {
        return new Province(provinceId, "ALAVA", "alava", "Vitoria", new Comunidad(), 1);
    }

    private Country createCountry(Long countryId) {
        return new Country(countryId, "SPAIN", "spain", 1);
    }

    private String createAddressNumber() {
        return "680728473";
    }

    private Long createAddressProvinceId() {
        return 1L;
    }

    private String createAddressPostcode() {
        return "01008";
    }

    private String createAddressCity() {
        return "Madrid";
    }

    private Long createAddressCountryId() {
        return 1L;
    }

    private String createAddressStreet() {
        return "Pintor Clemente Arraiz 9 3C";
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileAddressAddTestError() throws Exception {
        String street = createAddressStreet();
        Long countryId = createAddressCountryId();
        String city = createAddressCity();
        String postcode = createAddressPostcode();
        Long provinceId = createAddressProvinceId();
        String number = createAddressNumber();
        Country country = createCountry(countryId);
        Province province = createProvince(provinceId);

        Address address = createAddress(street, country, city, postcode, province, number);

        when(countryServiceImpl.findById(countryId)).thenReturn(country);
        when(provinceServiceImpl.findById(provinceId)).thenReturn(province);
        when(addressServiceImpl.save(address)).thenReturn(null);

        mockMvc.perform(post("https://localhost/profile/address/add")
                .with(csrf())
                .param("street", street)
                .param("countryId", countryId.toString())
                .param("city", city)
                .param("postcode", postcode)
                .param("provinceId", provinceId.toString())
                .param("number", number))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"))
                .andExpect(view().name("redirect:/profile/address"));

    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileAddressAddTestFormError() throws Exception {
        String street = createAddressStreet();
        Long countryId = createAddressCountryId();
        String city = createAddressCity();
        String postcode = createWrongAddressPostcode();
        Long provinceId = createAddressProvinceId();
        String number = createAddressNumber();
        Country country = createCountry(countryId);
        Province province = createProvince(provinceId);

        Address address = createAddress(street, country, city, postcode, province, number);

        when(countryServiceImpl.findById(countryId)).thenReturn(country);
        when(provinceServiceImpl.findById(provinceId)).thenReturn(province);
        when(addressServiceImpl.save(address)).thenReturn(null);

        mockMvc.perform(post("https://localhost/profile/address/add")
                .with(csrf())
                .param("street", street)
                .param("countryId", countryId.toString())
                .param("city", city)
                .param("postcode", postcode)
                .param("provinceId", provinceId.toString())
                .param("number", number))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attributeExists("form"))
                .andExpect(model().attributeExists("provinces"))
                .andExpect(model().attributeExists("countries"))
                .andExpect(view().name("page/profile_address_edit"));

    }

    private String createWrongAddressPostcode() {
        return "1";
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void getProfileAddressEditTestSuccess() throws Exception {
        Address address = createNotCompleteAddress();

        when(addressServiceImpl.findById(1L)).thenReturn(address);

        mockMvc.perform(get("https://localhost/profile/address/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("page/profile_address_edit"))
                .andExpect(model().attributeExists("provinces"))
                .andExpect(model().attributeExists("countries"))
                .andExpect(model().attribute("address", address))
                .andExpect(model().attribute("user", basicUser));
    }

    private Address createNotCompleteAddress() {
        return new Address(1L, new Country(), new Province(), "Vitoria", "01008", "Pintor Clemente Arraiz", "680728473",
                basicUser, true, true, 1);
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void getProfileAddressEditTestError() throws Exception {
        Address address = createNotCompleteAddress();
        address.setUser(null);

        when(addressServiceImpl.findById(1L)).thenReturn(address);

        mockMvc.perform(get("https://localhost/profile/address/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/profile/address"));
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileAddressEditTestSuccess() throws Exception {
        String street = createAddressStreet();
        String street2 = createAddressStreet2();
        Long countryId = createAddressCountryId();
        String city = createAddressCity();
        String postcode = createAddressPostcode();
        Long provinceId = createAddressProvinceId();
        String number = createAddressNumber();
        Country country = createCountry(countryId);
        Province province = createProvince(provinceId);

        Address address = createAddress(street, country, city, postcode, province, number);
        Address addressChanged = createAddress(street2, country, city, postcode, province, number);

        when(addressServiceImpl.findById(1L)).thenReturn(address);
        when(countryServiceImpl.findById(countryId)).thenReturn(country);
        when(provinceServiceImpl.findById(provinceId)).thenReturn(province);
        when(addressServiceImpl.save(address)).thenReturn(addressChanged);

        mockMvc.perform(post("https://localhost/profile/address/edit/1")
                .with(csrf())
                .param("street", street2)
                .param("countryId", countryId.toString())
                .param("city", city)
                .param("postcode", postcode)
                .param("provinceId", provinceId.toString())
                .param("number", number))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/profile/address"));
    }

    private String createAddressStreet2() {
        return "street 2";
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileAddressEditTestError() throws Exception {
        String street = createAddressStreet();
        String street2 = createAddressStreet2();
        Long countryId = createAddressCountryId();
        String city = createAddressCity();
        String postcode = createAddressPostcode();
        Long provinceId = createAddressProvinceId();
        String number = createAddressNumber();
        Country country = createCountry(countryId);
        Province province = createProvince(provinceId);

        Address address = createAddress(street, country, city, postcode, province, number);

        when(addressServiceImpl.findById(1L)).thenReturn(address);
        when(countryServiceImpl.findById(countryId)).thenReturn(country);
        when(provinceServiceImpl.findById(provinceId)).thenReturn(province);
        when(addressServiceImpl.save(address)).thenReturn(null);

        mockMvc.perform(post("https://localhost/profile/address/edit/1")
                .with(csrf())
                .param("street", street2)
                .param("countryId", countryId.toString())
                .param("city", city)
                .param("postcode", postcode)
                .param("provinceId", provinceId.toString())
                .param("number", number))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"))
                .andExpect(view().name("redirect:/profile/address"));
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileAddressEditTestFormError() throws Exception {
        String street = createAddressStreet();
        String street2 = createAddressStreet2();
        Long countryId = createAddressCountryId();
        String city = createAddressCity();
        String postcode = createWrongAddressPostcode();
        Long provinceId = createAddressProvinceId();
        String number = createAddressNumber();
        Country country = createCountry(countryId);
        Province province = createProvince(provinceId);

        Address address = createAddress(street, country, city, postcode, province, number);

        when(addressServiceImpl.findById(1L)).thenReturn(address);
        when(countryServiceImpl.findById(countryId)).thenReturn(country);
        when(provinceServiceImpl.findById(provinceId)).thenReturn(province);
        when(addressServiceImpl.save(address)).thenReturn(null);

        mockMvc.perform(post("https://localhost/profile/address/edit/1")
                .with(csrf())
                .param("street", street2)
                .param("countryId", countryId.toString())
                .param("city", city)
                .param("postcode", postcode)
                .param("provinceId", provinceId.toString())
                .param("number", number))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attributeExists("form"))
                .andExpect(model().attributeExists("provinces"))
                .andExpect(model().attributeExists("countries"))
                .andExpect(view().name("page/profile_address_edit"));
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileAddressEditSetDefault() throws Exception {
        Address address = createAddressNotDefault();

        when(addressServiceImpl.findById(2L)).thenReturn(address);
        when(addressServiceImpl.save(address)).thenReturn(address);
        when(userServiceImpl.save(basicUser)).thenReturn(basicUser);

        mockMvc.perform(post("https://localhost/profile/address/edit/set-default/2")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/profile/address"));
    }

    private Address createAddressNotDefault() {
        return new Address(2L, null, null, null, null, null, null, basicUser, true, false, 1);
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfileDeleteAddress() throws Exception {
        Address address = createNotCompleteAddress();
        Address addressDisabled = createNotCompleteAddress();
        addressDisabled.setEnabled(false);
        addressDisabled.setDefaultAddress(false);

        List<Address> addresses = Stream
                .of(new Address(2L, null, null, null, null, null, null, basicUser, true, false, 1))
                .collect(Collectors.toList());

        when(addressServiceImpl.findById(1L)).thenReturn(address);
        when(addressServiceImpl.disable(address)).thenReturn(addressDisabled);
        when(userServiceImpl.getUserAddresses(basicUser)).thenReturn(addresses);

        mockMvc.perform(post("https://localhost/profile/address/delete/1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/profile/address"));

    }

    @Test
    @WithUserDetails(value = "testyUser")
    void getProfilePaymentMethodTest() throws Exception {

        when(userServiceImpl.getUserPaymentMethods(basicUser)).thenReturn(basicUser.getPaymentMethods());

        mockMvc.perform(get("https://localhost/profile/payment-method"))
                .andExpect(status().isOk())
                .andExpect(view().name("page/profile_payment_method"))
                .andExpect(model().attributeExists("paymentMethods"))
                .andExpect(model().attribute("user", basicUser));
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void getProfilePaymentMethodAddTest() throws Exception {

        mockMvc.perform(get("https://localhost/profile/payment-method/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("page/profile_payment_method_edit"))
                .andExpect(model().attribute("user", basicUser));
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfilePaymentMethodAddTestSuccess() throws Exception {
        String cardNumber = createPaymentCardNumber();
        String cardHolderName = createPaymentCardHolderName();
        String securityCode = createPaymentSecurityCode();
        Long expirationYear = createPaymentExpirationYear();
        Long expirationMonth = createPaymentExpirationMonth();
        PaymentMethod paymentMethod = createPaymentMethod(cardNumber, cardHolderName, securityCode, expirationYear,
                expirationMonth);
        PaymentMethod paymentMethodSaved = createPaymentMethod(cardNumber, cardHolderName, securityCode, expirationYear,
                expirationMonth);
        paymentMethodSaved.setId(1L);
        paymentMethodSaved.setVersion(1);

        when(paymentMethodServiceImpl.save(paymentMethod)).thenReturn(paymentMethodSaved);

        mockMvc.perform(post("https://localhost/profile/payment-method/add")
                .with(csrf())
                .param("cardNumber", cardNumber)
                .param("cardHolderName", cardHolderName)
                .param("securityCode", securityCode)
                .param("expirationYear", expirationYear.toString())
                .param("expirationMonth", expirationMonth.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/profile/payment-method"));
    }

    private PaymentMethod createPaymentMethod(String cardNumber, String cardHolderName, String securityCode,
            Long expirationYear, Long expirationMonth) {
        return new PaymentMethod(null, basicUser, cardHolderName, cardNumber, expirationMonth, expirationYear,
                securityCode, true, true, null);
    }

    private Long createPaymentExpirationMonth() {
        return 5L;
    }

    private Long createPaymentExpirationYear() {
        return 2023L;
    }

    private String createPaymentSecurityCode() {
        return "111";
    }

    private String createPaymentCardHolderName() {
        return "Aritz Domaika Peirats";
    }

    private String createPaymentCardNumber() {
        return "1111222233334444";
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfilePaymentMethodAddTestError() throws Exception {
        String cardNumber = createPaymentCardNumber();
        String cardHolderName = createPaymentCardHolderName();
        String securityCode = createPaymentSecurityCode();
        Long expirationYear = createPaymentExpirationYear();
        Long expirationMonth = createPaymentExpirationMonth();
        PaymentMethod paymentMethod = createPaymentMethod(cardNumber, cardHolderName, securityCode, expirationYear,
                expirationMonth);

        when(paymentMethodServiceImpl.save(paymentMethod)).thenReturn(null);

        mockMvc.perform(post("https://localhost/profile/payment-method/add")
                .with(csrf())
                .param("cardNumber", cardNumber)
                .param("cardHolderName", cardHolderName)
                .param("securityCode", securityCode)
                .param("expirationYear", expirationYear.toString())
                .param("expirationMonth", expirationMonth.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"))
                .andExpect(view().name("redirect:/profile/payment-method"));
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfilePaymentMethodAddTestFormError() throws Exception {
        String cardNumber = createWrongPaymentCardNumber();
        String cardHolderName = createPaymentCardHolderName();
        String securityCode = createPaymentSecurityCode();
        Long expirationYear = createPaymentExpirationYear();
        Long expirationMonth = createPaymentExpirationMonth();
        PaymentMethod paymentMethod = createPaymentMethod(cardNumber, cardHolderName, securityCode, expirationYear,
                expirationMonth);

        when(paymentMethodServiceImpl.save(paymentMethod)).thenReturn(null);

        mockMvc.perform(post("https://localhost/profile/payment-method/add")
                .with(csrf())
                .param("cardNumber", cardNumber)
                .param("cardHolderName", cardHolderName)
                .param("securityCode", securityCode)
                .param("expirationYear", expirationYear.toString())
                .param("expirationMonth", expirationMonth.toString()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attributeExists("form"))
                .andExpect(view().name("page/profile_payment_method_edit"));
    }

    private String createWrongPaymentCardNumber() {
        return "1";
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void getProfilePaymentMethodEditTestSuccess() throws Exception {
        PaymentMethod paymentMethod = getPaymentMethod();
        when(paymentMethodServiceImpl.findById(1L)).thenReturn(paymentMethod);

        mockMvc.perform(get("https://localhost/profile/payment-method/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("page/profile_payment_method_edit"))
                .andExpect(model().attribute("paymentMethod", paymentMethod))
                .andExpect(model().attribute("user", basicUser));
    }

    private PaymentMethod getPaymentMethod() {
        return new PaymentMethod(1L, basicUser, "Aritz Domaika Peirats", "5555666677778888", 1L, 2027L, "222", true,
                true, 1);
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void getProfilePaymentMethodEditTestError() throws Exception {
        PaymentMethod paymentMethod = getPaymentMethod();
        paymentMethod.setUser(null);
        when(paymentMethodServiceImpl.findById(1L)).thenReturn(paymentMethod);

        mockMvc.perform(get("https://localhost/profile/payment-method/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/profile/payment-method"));
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfilePaymentMethodEditTestSuccess() throws Exception {
        String cardNumber = createPaymentCardNumber();
        String cardHolderName = createPaymentCardHolderName();
        String securityCode = createPaymentSecurityCode();
        String securityCode2 = createPaymentSecurityCode2();
        Long expirationYear = createPaymentExpirationYear();
        Long expirationMonth = createPaymentExpirationMonth();
        PaymentMethod paymentMethod = createPaymentMethod(cardNumber, cardHolderName, securityCode, expirationYear,
                expirationMonth);
        PaymentMethod paymentMethod2 = createPaymentMethod(cardNumber, cardHolderName, securityCode2, expirationYear,
                expirationMonth);

        when(paymentMethodServiceImpl.findById(1L)).thenReturn(paymentMethod);
        when(paymentMethodServiceImpl.save(paymentMethod)).thenReturn(paymentMethod2);

        mockMvc.perform(post("https://localhost//profile/payment-method/edit/1")
                .with(csrf())
                .param("cardNumber", cardNumber)
                .param("cardHolderName", cardHolderName)
                .param("securityCode", securityCode)
                .param("expirationYear", expirationYear.toString())
                .param("expirationMonth", expirationMonth.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/profile/payment-method"));
    }

    private String createPaymentSecurityCode2() {
        return "987";
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfilePaymentMethodEditTestError() throws Exception {
        String cardNumber = createPaymentCardNumber();
        String cardHolderName = createPaymentCardHolderName();
        String securityCode = createPaymentSecurityCode();
        Long expirationYear = createPaymentExpirationYear();
        Long expirationMonth = createPaymentExpirationMonth();
        PaymentMethod paymentMethod = createPaymentMethod(cardNumber, cardHolderName, securityCode, expirationYear,
                expirationMonth);

        when(paymentMethodServiceImpl.findById(1L)).thenReturn(paymentMethod);
        when(paymentMethodServiceImpl.save(paymentMethod)).thenReturn(null);

        mockMvc.perform(post("https://localhost//profile/payment-method/edit/1")
                .with(csrf())
                .param("cardNumber", cardNumber)
                .param("cardHolderName", cardHolderName)
                .param("securityCode", securityCode)
                .param("expirationYear", expirationYear.toString())
                .param("expirationMonth", expirationMonth.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"))
                .andExpect(view().name("redirect:/profile/payment-method"));
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfilePaymentMethodEditTestFormError() throws Exception {
        String cardNumber = createWrongPaymentCardNumber();
        String cardHolderName = createPaymentCardHolderName();
        String securityCode = createPaymentSecurityCode();
        Long expirationYear = createPaymentExpirationYear();
        Long expirationMonth = createPaymentExpirationMonth();

        mockMvc.perform(post("https://localhost//profile/payment-method/edit/1")
                .with(csrf())
                .param("cardNumber", cardNumber)
                .param("cardHolderName", cardHolderName)
                .param("securityCode", securityCode)
                .param("expirationYear", expirationYear.toString())
                .param("expirationMonth", expirationMonth.toString()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attributeExists("form"))
                .andExpect(view().name("page/profile_payment_method_edit"));
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfilePaymentMethodSetDefaultTest() throws Exception {

        when(paymentMethodServiceImpl.findById(2L)).thenReturn(basicUser.getPaymentMethods().get(1));
        when(userServiceImpl.save(basicUser)).thenReturn(basicUser);

        mockMvc.perform(post("https://localhost/profile/payment-method/set-default/2")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/profile/payment-method"));
    }

    @Test
    @WithUserDetails(value = "testyUser")
    void postProfilePaymentMethodDelete() throws Exception {
        PaymentMethod paymentMethod = getPaymentMethod();
        PaymentMethod paymentMethodDisabled = getPaymentMethod();
        paymentMethodDisabled.setEnabled(false);
        paymentMethodDisabled.setDefaultMethod(false);

        List<PaymentMethod> paymentMethods = Stream
                .of(new PaymentMethod(2L, basicUser, "Aritz Peirats Domaika", "5555666677778888", 2L, 2024L, "1", false,
                        true, 1))
                .collect(Collectors.toList());

        when(paymentMethodServiceImpl.findById(1L)).thenReturn(paymentMethod);
        when(paymentMethodServiceImpl.disable(paymentMethod)).thenReturn(paymentMethodDisabled);
        when(userServiceImpl.getUserPaymentMethods(basicUser)).thenReturn(paymentMethods);

        mockMvc.perform(post("https://localhost/profile/payment-method/delete/1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/profile/payment-method"));

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

}