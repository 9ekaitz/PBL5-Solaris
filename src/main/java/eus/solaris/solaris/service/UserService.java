package eus.solaris.solaris.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserRegistrationForm;

public interface UserService {
    public User save(User user);
    public User findByUsername(String username);
    public void deleteUser(Long id);
    public User disableUser(User user);
    public User editPassword(String newPassword, String oldPassword, Authentication authentication);
    public User editUser(String name, String firstSurname, String secondSurname, String email, Authentication authentication);
    public List<Address> getUserAddresses(Authentication authentication);
    public List<PaymentMethod> getUserPaymentMethods(Authentication authentication);
    public User register(UserRegistrationForm userRegistrationForm);
}
