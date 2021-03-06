package eus.solaris.solaris.service;

import java.io.Serializable;
import java.util.List;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.exception.AvatarNotCreatedException;
import eus.solaris.solaris.form.UserInformationEditForm;
import eus.solaris.solaris.form.UserRegistrationForm;

public interface UserService extends Serializable {
    public User save(User user);

    public User findByUsername(String username);

    public void deleteUser(Long id);

    public User disableUser(User user);

    public User editPassword(String newPassword, String oldPassword, User user);

    public List<Address> getUserAddresses(User user);

    public List<PaymentMethod> getUserPaymentMethods(User user);

    public User register(UserRegistrationForm userRegistrationForm) throws AvatarNotCreatedException;

    public User editUser(UserInformationEditForm form, User attribute);

    public User findById(Long id);
}
