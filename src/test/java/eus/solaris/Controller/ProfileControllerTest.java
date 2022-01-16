package eus.solaris.Controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.aspectj.lang.annotation.Before;

import eus.solaris.solaris.SolarisApplication;
import eus.solaris.solaris.controller.ProfileController;
import eus.solaris.solaris.controller.UserControllerAdvice;
import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserPasswordModificationForm;
import eus.solaris.solaris.security.UserDetailsServiceImpl;
import eus.solaris.solaris.service.UserService;
import eus.solaris.solaris.service.impl.AddressServiceImpl;
import eus.solaris.solaris.service.impl.CountryServiceImpl;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.PaymentMethodImpl;
import eus.solaris.solaris.service.impl.ProvinceServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers =  {ProfileController.class, UserControllerAdvice.class})
@ContextConfiguration(classes = SolarisApplication.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
	UserServiceImpl userServiceImpl;

    @MockBean
    UserService userService;

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

    @MockBean
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private Authentication authentication;

    @Mock
    UserPasswordModificationForm userPasswordModificationForm;



}