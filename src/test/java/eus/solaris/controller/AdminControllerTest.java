// package eus.solaris.controller;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Import;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.servlet.MockMvc;

// import eus.solaris.solaris.domain.User;
// import eus.solaris.solaris.service.UserServiceImpl;
// import eus.solaris.validation.SpringWebAuxTestConfig;

// @Import(SpringWebAuxTestConfig.class)
// @ExtendWith(SpringExtension.class)
// @WebMvcTest({/*ProfileController.class, UserControllerAdvice.class*/})
// public class AdminControllerTest {
    
//     @Autowired
//     MockMvc mockMvc;

//     @MockBean
// 	UserServiceImpl userServiceImpl;

//     @MockBean
// 	PasswordEncoder passwordEncoder;

//     Authentication authentication;
//     User basicUser;
//     ObjectMapper objectMapper = new ObjectMapper();
// }
