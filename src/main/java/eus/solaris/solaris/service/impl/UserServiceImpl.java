package eus.solaris.solaris.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserRegistrationForm;
import eus.solaris.solaris.repository.UserRepository;
import eus.solaris.solaris.service.RoleService;
import eus.solaris.solaris.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
	PasswordEncoder passwordEncoder;

    @Override
    public User register(UserRegistrationForm userRegistrationForm) {
        User user = modelMapper.map(userRegistrationForm, User.class);
        user.setRole(roleService.findByName("ROLE_USER"));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User disableUser(User user) {
        user.setEnabled(false);
        return userRepository.save(user);
    }

    @Override
    public User editPassword(String newPassword, String oldPassword, Authentication authentication) {
        User user = findByUsername(authentication.getName());

        if(BCrypt.checkpw(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            user = save(user);
        }     

        return user;
    }

    @Override
    public User editUser(String name, String firstSurname, String secondSurname, String email, Authentication authentication) {
        User user = findByUsername(authentication.getName());
        user.setEmail(email);
        user.setName(name);
        user.setFirstSurname(firstSurname);
        user.setSecondSurname(secondSurname);
        return save(user);    
    }

    @Override
    public List<Address> getUserAddresses(Authentication authentication) {
        User user = findByUsername(authentication.getName());
        List<Address> addresses = new ArrayList<>();

        for(int i = 0; i < user.getAddresses().size(); i++){
            Address address = user.getAddresses().toArray(new Address[user.getAddresses().size()])[i];
            if(address.isEnabled()){
                addresses.add(address);
            }
        }

        user.setAddresses(addresses);

        return user.getAddresses();
    }

    @Override
    public List<PaymentMethod> getUserPaymentMethods(Authentication authentication) {
        User user = findByUsername(authentication.getName());
        List<PaymentMethod> paymentMethods = new ArrayList<>();

        for(int i = 0; i < user.getPaymentMethods().size(); i++){
            PaymentMethod paymentMethod = user.getPaymentMethods().toArray(new PaymentMethod[user.getPaymentMethods().size()])[i];
            if(paymentMethod.isEnabled()){
                paymentMethods.add(paymentMethod);
            }
        }

        user.setPaymentMethods(paymentMethods);
        
        return user.getPaymentMethods();
    }
}
