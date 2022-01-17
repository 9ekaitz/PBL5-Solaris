package eus.solaris.solaris.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

import eus.solaris.solaris.config.SpringWebAuxTestConfig;
import eus.solaris.solaris.controller.ProfileController;
import eus.solaris.solaris.controller.UserControllerAdvice;
import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.Country;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.Privilege;
import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.impl.AddressServiceImpl;
import eus.solaris.solaris.service.impl.CountryServiceImpl;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.PaymentMethodImpl;
import eus.solaris.solaris.service.impl.ProvinceServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;

@Import(SpringWebAuxTestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest({ProfileController.class, UserControllerAdvice.class})
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
    PaymentMethodImpl paymentMethodServiceImpl;

    @MockBean
    LanguageServiceImpl languageServiceImpl;

    @Autowired
    UserDetailsService userDetailsService;

    Authentication authentication;
    User basicUser;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void loadUser(){
        String username = "testyUser";
        Privilege privilege = createUserLoggedPrivilege();

        Role ROLE_USER = new Role(1L, "ROLE_USER", true, null,  Stream
            .of(privilege)
            .collect(Collectors.toSet()), 1);

        List<Address> addresses = Stream
        .of(new Address(1L, new Country(), new Province(), "Vitoria", "01008", "Pintor Clemente Arraiz", "680728473", basicUser, true, true, 1),
            new Address(2L, null, null, null, null, null, null, basicUser, true, false, 1))
        .collect(Collectors.toList());

        List<PaymentMethod> paymentMethods = Stream
        .of(new PaymentMethod(1L, basicUser, "Aritz Domaika Peirats", "5555666677778888", 1L, 2027L, "222", true, true, 1)).collect(Collectors.toList());

        basicUser = new User(1L, "testyUser", "testy@foo", "foo123", "Testy", "Tester", "User", true, addresses, paymentMethods, ROLE_USER, null, 1);

        when(userServiceImpl.findByUsername(username)).thenReturn(basicUser);
    }


    @Test
    @WithUserDetails(value="testyUser")
    void getProfileTest() throws Exception{        

        mockMvc.perform(get("https://localhost/profile"))
            .andExpect(status().isOk())
            .andExpect(view().name("page/profile"))
            .andExpect(model().attribute("user", basicUser));
    }

    @Test
    void getProfileNotLoggedTest() throws Exception{        

        mockMvc.perform(get("https://localhost/profile"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails(value="testyUser")
    void getProfileSecurityTest() throws Exception{

        mockMvc.perform(get("https://localhost/profile/security"))
            .andExpect(status().isOk())
            .andExpect(view().name("page/profile_security"))
            .andExpect(model().attributeExists("form"))
            .andExpect(model().attribute("user", basicUser));
    }

    @Test
    @WithUserDetails(value="testyUser")
    void postProfileSecurityTestTrue() throws Exception {
        String oldPassword = "foo123";
        String verifyNewPasword = "bar123";
        User basicUserModify = new User(1L, "testyUser", "testy@foo", "bar123", "Testy", "Tester", "User", true, null, null, null, null, 1);

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

    @Test
    @WithUserDetails(value="testyUser")
    void postProfileSecurityTestFalse() throws Exception {
        String oldPassword = "foo123";
        String verifyNewPasword = "bar123";

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
    @WithUserDetails(value="testyUser")
    void getProfileDeleteAccountTest() throws Exception{

        mockMvc.perform(get("https://localhost/profile/delete-account"))
            .andExpect(status().isOk())
            .andExpect(view().name("page/delete_account"))
            .andExpect(model().attribute("user", basicUser));

    }

    @Test
    @WithUserDetails(value="testyUser")
    void postProfileDeleteAccountTestSuccess() throws Exception {

        User basicUserModify = new User(1L, "testyUser", "testy@foo", "foo123", "Testy", "Tester", "User", false, null, null, null, null, 1);

        when(userServiceImpl.disableUser(basicUser)).thenReturn(basicUserModify);

        mockMvc.perform(post("https://localhost/profile/delete-account")
        .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("success"))
        .andExpect(view().name("redirect:/logout"));

    }

    @Test
    @WithUserDetails(value="testyUser")
    void postProfileDeleteAccountTestError() throws Exception {

        User basicUserModify = new User(1L, "testyUser", "testy@foo", "foo123", "Testy", "Tester", "User", true, null, null, null, null, 1);

        when(userServiceImpl.disableUser(basicUser)).thenReturn(basicUserModify);

        mockMvc.perform(post("https://localhost/profile/delete-account")
        .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("error"))
        .andExpect(view().name("redirect:/logout"));

    }

    @Test
    @WithUserDetails(value="testyUser")
    void getProfileEditTest() throws Exception{
            
        mockMvc.perform(get("https://localhost/profile/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("page/profile_edit"));
    }

    @Test
    @WithUserDetails(value="testyUser")
    void postProfileEditTestSuccess() throws Exception {
        String name = "testyUser";
        String firstSurname = "testy";
        String secondSurname = "tester";
        String email = "testy@foo.com";

        User basicUserModify = new User(1L, "testyUser", "testy@foo.com", "foo123", "testyUser", "testy", "tester", true, null, null, null, null, 1);

        when(userServiceImpl.editUser(name, firstSurname, secondSurname, email, basicUser)).thenReturn(basicUserModify);

        mockMvc.perform(post("https://localhost/profile/edit")
        .with(csrf())
        .param("name", name)
        .param("firstSurname", firstSurname)
        .param("secondSurname", secondSurname)
        .param("email", email))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("success"))
        .andExpect(view().name("redirect:/profile"));

    }

    @Test
    @WithUserDetails(value="testyUser")
    void postProfileEditTestError() throws Exception {
        String name = "testyUser";
        String firstSurname = "testy";
        String secondSurname = "tester";
        String email = "testy@foo.com";

        when(userServiceImpl.editUser(name, firstSurname, secondSurname, email, basicUser)).thenReturn(null);

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
    @WithUserDetails(value="testyUser")
    void postProfileEditTestFormFailed() throws Exception {
        String name = "testyUser";
        String firstSurname = "testy";
        String secondSurname = "tester";
        String email = "testy";

        User basicUserModify = new User(1L, "testyUser", "testy", "foo123", "testyUser", "testy", "tester", true, null, null, null, null, 1);

        when(userServiceImpl.editUser(name, firstSurname, secondSurname, email, basicUser)).thenReturn(basicUserModify);

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

    //     when(userServiceImpl.getUserAddresses(basicUser)).thenReturn(basicUser.getAddresses());
   
    //     mockMvc.perform(get("https://localhost/profile/address"))
    //         .andExpect(status().isOk())
    //         .andExpect(view().name("page/profile_address"))
    //         .andExpect(model().attributeExists("addresses"))
    //         .andExpect(model().attribute("user", basicUser));
    // }

    @Test
    @WithUserDetails(value="testyUser")
    void getProfileAddressAddTest() throws Exception{
                    
        mockMvc.perform(get("https://localhost/profile/address/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("page/profile_address_edit"))
            .andExpect(model().attributeExists("provinces"))
            .andExpect(model().attributeExists("countries"))
            .andExpect(model().attribute("user", basicUser));
    }

    @Test
    @WithUserDetails(value="testyUser")
    void postProfileAddressAddTestSuccess() throws Exception {
        String street = "Pintor Clemente Arraiz 9 3C";
        Long countryId = 1L;
        String city = "Madrid";
        String postcode = "01008";
        Long provinceId = 1L;
        String number = "680728473";
        Country country = new Country(countryId, "SPAIN", "spain", 1);
        Province province = new Province(provinceId, "ALAVA", "alava", 1);
        Address address = new Address(null, country, province, city, postcode, street, number, basicUser, true, true, null);
        Address addressChanged = new Address(1L, country, province, city, postcode, street, number, basicUser, true, true, 1);

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

    @Test
    @WithUserDetails(value="testyUser")
    void postProfileAddressAddTestError() throws Exception {
        String street = "Pintor Clemente Arraiz 9 3C";
        Long countryId = 1L;
        String city = "Madrid";
        String postcode = "01008";
        Long provinceId = 1L;
        String number = "680728473";
        Country country = new Country(countryId, "SPAIN", "spain", 1);
        Province province = new Province(provinceId, "ALAVA", "alava", 1);
        Address address = new Address(null, country, province, city, postcode, street, number, basicUser, true, true, null);

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
    @WithUserDetails(value="testyUser")
    void postProfileAddressAddTestFormError() throws Exception {
        String street = "Pintor Clemente Arraiz 9 3C";
        Long countryId = 1L;
        String city = "Madrid";
        String postcode = "018";
        Long provinceId = 1L;
        String number = "6807284";
        Country country = new Country(countryId, "SPAIN", "spain", 1);
        Province province = new Province(provinceId, "ALAVA", "alava", 1);
        Address address = new Address(null, country, province, city, postcode, street, number, basicUser, true, true, null);

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

    @Test
    @WithUserDetails(value="testyUser")
    void getProfileAddressEditTestSuccess() throws Exception{

        Address address = new Address(1L, new Country(), new Province(), "Vitoria", "01008", "Pintor Clemente Arraiz", "680728473", basicUser, true, true, 1);
        when(addressServiceImpl.findById(1L)).thenReturn(address);
                            
        mockMvc.perform(get("https://localhost/profile/address/edit/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("page/profile_address_edit"))
            .andExpect(model().attributeExists("provinces"))
            .andExpect(model().attributeExists("countries"))
            .andExpect(model().attribute("address", address))
            .andExpect(model().attribute("user", basicUser));
    }

    @Test
    @WithUserDetails(value="testyUser")
    void getProfileAddressEditTestError() throws Exception{

        Address address = new Address(1L, new Country(), new Province(), "Vitoria", "01008", "Pintor Clemente Arraiz", "680728473", null, true, true, 1);
        when(addressServiceImpl.findById(1L)).thenReturn(address);
                            
        mockMvc.perform(get("https://localhost/profile/address/edit/1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/profile/address"));
    }

    @Test
    @WithUserDetails(value="testyUser")
    void postProfileAddressEditTestSuccess() throws Exception {
        String street = "Pintor Clemente Arraiz 9 3C";
        String steet2 = "Street 2";
        Long countryId = 1L;
        String city = "Madrid";
        String postcode = "01008";
        Long provinceId = 1L;
        String number = "680728473";
        Country country = new Country(countryId, "SPAIN", "spain", 1);
        Province province = new Province(provinceId, "ALAVA", "alava", 1);
        Address address = new Address(1L, country, province, city, postcode, street, number, basicUser, true, true, 1);
        Address addressChanged = new Address(1L, country, province, city, postcode, steet2, number, basicUser, true, true, 1);

        when(addressServiceImpl.findById(1L)).thenReturn(address);
        when(countryServiceImpl.findById(countryId)).thenReturn(country);
        when(provinceServiceImpl.findById(provinceId)).thenReturn(province);
        when(addressServiceImpl.save(address)).thenReturn(addressChanged);

        mockMvc.perform(post("https://localhost/profile/address/edit/1")
        .with(csrf())
        .param("street", steet2)
        .param("countryId", countryId.toString())
        .param("city", city)
        .param("postcode", postcode)
        .param("provinceId", provinceId.toString())
        .param("number", number))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("success"))
        .andExpect(view().name("redirect:/profile/address"));
    }
    
    @Test
    @WithUserDetails(value="testyUser")
    void postProfileAddressEditTestError() throws Exception {
        String street = "Pintor Clemente Arraiz 9 3C";
        String steet2 = "Street 2";
        Long countryId = 1L;
        String city = "Madrid";
        String postcode = "01008";
        Long provinceId = 1L;
        String number = "680728473";
        Country country = new Country(countryId, "SPAIN", "spain", 1);
        Province province = new Province(provinceId, "ALAVA", "alava", 1);
        Address address = new Address(1L, country, province, city, postcode, street, number, basicUser, true, true, 1);

        when(addressServiceImpl.findById(1L)).thenReturn(address);
        when(countryServiceImpl.findById(countryId)).thenReturn(country);
        when(provinceServiceImpl.findById(provinceId)).thenReturn(province);
        when(addressServiceImpl.save(address)).thenReturn(null);

        mockMvc.perform(post("https://localhost/profile/address/edit/1")
        .with(csrf())
        .param("street", steet2)
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
    @WithUserDetails(value="testyUser")
    void postProfileAddressEditTestFormError() throws Exception {
        String street = "Pintor Clemente Arraiz 9 3C";
        String steet2 = "Street 2";
        Long countryId = 1L;
        String city = "Madrid";
        String postcode = "01008";
        String postcode2 = "0";
        Long provinceId = 1L;
        String number = "680728473";
        Country country = new Country(countryId, "SPAIN", "spain", 1);
        Province province = new Province(provinceId, "ALAVA", "alava", 1);
        Address address = new Address(1L, country, province, city, postcode, street, number, basicUser, true, true, 1);

        when(addressServiceImpl.findById(1L)).thenReturn(address);
        when(countryServiceImpl.findById(countryId)).thenReturn(country);
        when(provinceServiceImpl.findById(provinceId)).thenReturn(province);
        when(addressServiceImpl.save(address)).thenReturn(null);

        mockMvc.perform(post("https://localhost/profile/address/edit/1")
        .with(csrf())
        .param("street", steet2)
        .param("countryId", countryId.toString())
        .param("city", city)
        .param("postcode", postcode2)
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
    @WithUserDetails(value="testyUser")
    void postprofileAddressEditSetDefault() throws Exception {
        Address address = new Address(2L, null, null, null, null, null, null, basicUser, true, false, 1);

        when(addressServiceImpl.findById(2L)).thenReturn(address);
        when(addressServiceImpl.save(address)).thenReturn(address);
        when(userServiceImpl.save(basicUser)).thenReturn(basicUser);

        mockMvc.perform(post("https://localhost/profile/address/edit/set-default/2")
        .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("success"))
        .andExpect(view().name("redirect:/profile/address"));
    }
    @Test
    @WithUserDetails(value="testyUser")
    void getProfilePaymentMethodTest() throws Exception {

        when(userServiceImpl.getUserPaymentMethods(basicUser)).thenReturn(basicUser.getPaymentMethods());

        mockMvc.perform(get("https://localhost/profile/payment-method"))
            .andExpect(status().isOk())
            .andExpect(view().name("page/profile_payment_method"))
            .andExpect(model().attributeExists("paymentMethods"))
            .andExpect(model().attribute("user", basicUser));
    }

    @Test
    @WithUserDetails(value="testyUser")
    void getProfilePaymentMethodAddTest() throws Exception{
                    
        mockMvc.perform(get("https://localhost/profile/payment-method/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("page/profile_payment_method_edit"))
            .andExpect(model().attributeExists("years"))
            .andExpect(model().attributeExists("months"))
            .andExpect(model().attribute("user", basicUser));
    }

    @Test
    @WithUserDetails(value="testyUser")
    void getProfilePaymentMethodEditTestSuccess() throws Exception {
        PaymentMethod paymentMethod = new PaymentMethod(1L, basicUser, "Aritz Domaika Peirats", "5555666677778888", 1L, 2027L, "222", true, true, 1);
        when(paymentMethodServiceImpl.findById(1L)).thenReturn(paymentMethod);
                            
        mockMvc.perform(get("https://localhost/profile/payment-method/edit/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("page/profile_payment_method_edit"))
            .andExpect(model().attributeExists("years"))
            .andExpect(model().attributeExists("months"))
            .andExpect(model().attribute("paymentMethod", paymentMethod))
            .andExpect(model().attribute("user", basicUser));
    }

    @Test
    @WithUserDetails(value="testyUser")
    void getProfilePaymentMethodEditTestError() throws Exception {
        PaymentMethod paymentMethod = new PaymentMethod(1L, null, "Aritz Domaika Peirats", "5555666677778888", 1L, 2027L, "222", true, true, 1);
        when(paymentMethodServiceImpl.findById(1L)).thenReturn(paymentMethod);
                            
        mockMvc.perform(get("https://localhost/profile/payment-method/edit/1"))
            .andExpect(status().is3xxRedirection())
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