package eus.solaris.solaris.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.mock.web.MockRequestDispatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import eus.solaris.solaris.config.SpringWebAuxTestConfig;
import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.Country;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.Privilege;
import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.DataEntryRepository;
import eus.solaris.solaris.repository.SolarPanelRepository;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@Import(SpringWebAuxTestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest({ CustomErrorController.class, UserControllerAdvice.class })
class CustomErrorControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userServiceImpl;

    @MockBean
    MockRequestDispatcher mockRequestDispatcher;

    @MockBean
    LanguageServiceImpl languageServiceImpl;

    @MockBean
    SolarPanelRepository solarPanelRepository;

    @MockBean
    DataEntryRepository dataEntryRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    User basicUser;

    @BeforeEach
    void loadUser() {
        String username = "testyUser";
        Privilege privilege = createUserLoggedPrivilege();

        Role ROLE_USER = new Role(1L, "ROLE_USER", true, null, Stream
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

    @Test
    @WithUserDetails (value = "testyUser")
    void error404() throws Exception {


        mockMvc.perform(get("https://localhost/error/"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/404"));
    }


    private User createUser(List<Address> addresses, List<PaymentMethod> paymentMethods, Role ROLE_USER) {
        return new User(1L, "testyUser", "testy@foo", "foo123", "Testy", "Tester", "User", true, addresses,
                paymentMethods, ROLE_USER, null, null, 1);
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
