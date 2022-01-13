package eus.solaris.solaris.service;

import java.util.Set;

import org.springframework.security.core.Authentication;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.User;

public interface UserService {
    public User save(User user);
    public User findByUsername(String username);
    public void deleteUser(Long id);
    public boolean editPassword(String newPassword, String oldPassword, Authentication authentication);
    public boolean editUser(String name, String firstSurname, String secondSurname, String email, Authentication authentication);
    public Set<Address> getUserAddresses(Authentication authentication);
    public Object getUserPaymentMethods(Authentication authentication);
}
