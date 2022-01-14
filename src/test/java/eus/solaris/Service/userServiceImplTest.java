package eus.solaris.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.Country;
import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserRegistrationForm;
import eus.solaris.solaris.repository.UserRepository;
import eus.solaris.solaris.service.RoleService;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplTest {

    @InjectMocks    
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
	private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RoleService roleService;

    @Mock
    private Authentication authentication;

    @Test
    protected void testRegister() {
        Role role = createRole();
        User user = createUser(role);
        UserRegistrationForm userRegistrationForm = createUserRegistrationForm();

        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(userRegistrationForm, User.class)).thenReturn(user);
        when(roleService.findByName("ROLE_USER")).thenReturn(role);
        assertEquals(user, userServiceImpl.register(userRegistrationForm));
    }

    @Test
    protected void testSave() {
        User user = new User();

        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userServiceImpl.save(user));

    }

    @Test
    protected void testFindByUsername(){
        User user1 = new User();
        User user2 = new User();
        user1.setName("test");
        user2.setName("test");

        when(userRepository.findByUsername("test")).thenReturn(user1);
        assertEquals(user2, userServiceImpl.findByUsername("test"));
    }

    @Test
    protected void testDisabledUser(){
        User user1 = new User();
        user1.setEnabled(false);
        User user2 = new User();
        user2.setEnabled(true);

        when(userRepository.save(user1)).thenReturn(user1);
        assertEquals(user2, userServiceImpl.disableUser(user2));
    }

    @Test
    protected editPasswordTest(){
        Role role = createRole();
        User userToBeChange = new User(1L, "aritz.domaika", "password", "Aritz", "domaika", "peirats", true, "aritz.domaika@gmail.com", null, null, role, null, 1);
        User userChanged = new User(1L, "aritz.domaika", "passwordChanged", "Aritz", "domaika", "peirats", true, "aritz.domaika@gmail.com", null, null, role, null, 1);

        when(authentication.getName()).thenReturn("Aritz");
        when(userRepository.findByUsername("Aritz")).thenReturn(userToBeChange);
        try (MockedStatic<BCrypt> utilities = Mockito.mockStatic(BCrypt.class)) {
            utilities.when(() -> BCrypt.checkpw("password", userToBeChange.getPassword())).thenReturn(true);
            when(passwordEncoder.encode("passwordChanged")).thenReturn("passwordChanged");
            when(userRepository.save(userToBeChange)).thenReturn(userToBeChange);
    
            assertEquals(userChanged, userServiceImpl.editPassword("passwordChanged", "password", authentication));
        }

    }

    @Test
    protected void editUserTest(){
        Role role = createRole();
        User userToBeChange = new User(1L, "aritz.domaika", "password", "Aritz", "domaika", "peirats", true, "aritz.domaika@gmail.com", null, null, role, null, 1);
        User userChanged = new User(1L, "aritz.domaika", "password", "AritzCambiado", "domaikaCambiado", "peirats", true, "aritz.domaika@gmail.com", null, null, role, null, 1);

        when(authentication.getName()).thenReturn("Aritz");
        when(userRepository.findByUsername("Aritz")).thenReturn(userToBeChange);
        when(userRepository.save(userToBeChange)).thenReturn(userToBeChange);
        assertEquals(userChanged, userServiceImpl.editUser("AritzCambiado", "domaikaCambiado", "peirats", "aritz.domaika@gmail.com", authentication));
    }

    // @Test
    // public void getUserAddressesTest(){
    //     Role role = createRole();
    //     List<Address> addresses = new ArrayList<>();
    //     List<Address> addressesEnable = new ArrayList<>();

    //     User user = new User(1L, "aritz.domaika", "password", "Aritz", "domaika", "peirats", true, "aritz.domaika@gmail.com", addresses, null, role, null, 1);
        
    //     addresses.add(new Address(1L, new Country(), new Province(), "Vitoria", "01008", "Pintor Clemente Arraiz", "680728473", user, true, true, 1));
    //     addresses.add(new Address(2L, new Country(), new Province(), "Donostia", "01008", "Donostia Clemente Arraiz", "680728473", user, false, false, 1));
        
        
    //     when(authentication.getName()).thenReturn("Aritz");
    //     when(userRepository.findByUsername("Aritz")).thenReturn(user);
    //     assertEquals(addressesEnable, userServiceImpl.getUserAddresses(authentication));
    // }

    private UserRegistrationForm createUserRegistrationForm() {
        UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
        userRegistrationForm.setUsername("Aritz");
        userRegistrationForm.setPassword("aritzelMejor18");
        userRegistrationForm.setVerifyPassword("aritzelMejor18");
        userRegistrationForm.setName("Aritz");
        userRegistrationForm.setFirstSurname("Mejor");
        userRegistrationForm.setSecondSurname("Mejor");
        userRegistrationForm.setEmail("aritz.domaika@alumni.mondragon.edu");
        return userRegistrationForm;
    }

    private User createUser(Role role) {
        User user = new User();
        user.setUsername("Aritz");
        user.setPassword("aritzelMejor18");
        when(passwordEncoder.encode(user.getPassword())).thenReturn("aritzelMejor18");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setName("Aritz");
        user.setFirstSurname("Mejor");
        user.setSecondSurname("Mejor");
        user.setEmail("aritz.domaika@alumni.mondragon.edu");
        user.setRole(role);
        user.setEnabled(true);
        return user;
    }

    private Role createRole() {
        Role role = new Role();
        role.setId(2L);
        role.setName("ROLE_USER");
        role.setEnabled(true);
        role.setVersion(0);
        return role;
    }

    
    
}
