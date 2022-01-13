package eus.solaris.solaris.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.UserRepository;
import eus.solaris.solaris.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
	PasswordEncoder passwordEncoder;

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
    public boolean editPassword(String newPassword, String oldPassword, Authentication authentication) {
        Boolean result = false;
        User user = findByUsername(authentication.getName());

        if(BCrypt.checkpw(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            save(user);
            result = true;
        }     

        return result;
    }

    @Override
    public boolean editUser(String name, String firstSurname, String secondSurname, String email, Authentication authentication) {
        Boolean result = false;
        User user = findByUsername(authentication.getName());
        user.setEmail(email);
        user.setName(name);
        user.setFirstSurname(firstSurname);
        user.setSecondSurname(secondSurname);
        User returnedUser = save(user);
        if(returnedUser != null) result = true;

        return result;
    
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
