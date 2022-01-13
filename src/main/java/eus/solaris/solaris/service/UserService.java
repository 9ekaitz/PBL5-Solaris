package eus.solaris.solaris.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.User;

public interface UserService {
    public User save(User user);
    public User findByUsername(String username);
    public void deleteUser(Long id);
    public void disableUser(User user);
    public boolean editPassword(String newPassword, String oldPassword, Authentication authentication);
    public boolean editUser(String name, String firstSurname, String secondSurname, String email, Authentication authentication);
    public List<Address> getUserAddresses(Authentication authentication);
    public List<PaymentMethod> getUserPaymentMethods(Authentication authentication);
}
