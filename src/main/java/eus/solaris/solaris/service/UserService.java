package eus.solaris.solaris.service;

import java.util.List;

import org.springframework.beans.support.PagedListHolder;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserProfileCreateForm;
import eus.solaris.solaris.form.UserProfileUpdateForm;
import java.io.Serializable;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.exception.AvatarNotCreatedException;
import eus.solaris.solaris.form.UserInformationEditForm;
import eus.solaris.solaris.form.UserRegistrationForm;

public interface UserService extends Serializable {
    public User save(User user);
    public User findByUsername(String username);
    public List<User> findAll();
    public User findById(Long id);
    public Boolean update(Long id, UserProfileUpdateForm upuf);
    public Boolean updateUserPassword(Long id, String password);
    public Boolean disable(User user);
    public List<User> findManageableUsers();
    public Boolean create(UserProfileCreateForm upcf);
    public PagedListHolder<User> getPagesFromUsersList(List<User> users);
    public void deleteUser(Long id);
    public User disableUser(User user);
    public User editPassword(String newPassword, String oldPassword, User user);
    public List<Address> getUserAddresses(User user);
    public List<PaymentMethod> getUserPaymentMethods(User user);
    public User register(UserRegistrationForm userRegistrationForm) throws AvatarNotCreatedException;
    public User editUser(UserInformationEditForm form, User attribute);
}
