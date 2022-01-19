package eus.solaris.solaris.service;

import java.util.List;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserInformationEditForm;
import eus.solaris.solaris.form.UserRegistrationForm;

public interface UserService {
    public User save(User user);
    public User findByUsername(String username);
    public void deleteUser(Long id);
    public User disableUser(User user);
    public User editPassword(String newPassword, String oldPassword, User user);
    public List<Address> getUserAddresses(User user);
    public List<PaymentMethod> getUserPaymentMethods(User user);
    public User register(UserRegistrationForm userRegistrationForm);
    public User editUser(UserInformationEditForm form, User attribute);
}
