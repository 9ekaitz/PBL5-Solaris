package eus.solaris.solaris.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import eus.solaris.solaris.config.SpringWebAuxTestConfig;
import eus.solaris.solaris.controller.ProfileController;
import eus.solaris.solaris.controller.UserControllerAdvice;
import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.Country;
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

    @Mock
    Authentication authentication;

    User basicUser;

    @BeforeEach
    void loadUser(){
        String username = "testyUser";
        Role ROLE_USER = new Role(1L, "ROLE_USER", true, null,  Stream
            .of(new Privilege(2L, "LOGGED_USER_VIEW", "logged.user.view", true, null, 1))
            .collect(Collectors.toSet()), 1);

        List<Address> addresses = Stream
        .of(new Address(1L, new Country(), new Province(), "Vitoria", "01008", "Pintor Clemente Arraiz", "680728473", null, true, true, 1))
        .collect(Collectors.toList());


        basicUser = new User(1L, "testyUser", "testy@foo", "foo123", "Testy", "Tester", "User", true, addresses, null, ROLE_USER, null, 1);
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
    void getProfileDeleteAccountTest() throws Exception{
            
        mockMvc.perform(get("https://localhost/profile/delete-account"))
            .andExpect(status().isOk())
            .andExpect(view().name("page/delete_account"))
            .andExpect(model().attribute("user", basicUser));

    }

    @Test
    @WithUserDetails(value="testyUser")
    void getProfileEditTest() throws Exception{
            
        mockMvc.perform(get("https://localhost/profile/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("page/profile_edit"));
    }

    // @Test
    // @WithUserDetails(value="testyUser")
    // void getProfileAddressTest() throws Exception {
                
    //     mockMvc.perform(get("https://localhost/profile/address"))
    //         .andExpect(status().isOk())
    //         .andExpect(view().name("page/profile_address"))
    //         .andExpect(model().attribute("addresses", basicUser.getAddresses()))
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


}