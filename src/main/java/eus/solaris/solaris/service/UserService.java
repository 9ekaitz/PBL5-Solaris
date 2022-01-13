package eus.solaris.solaris.service;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserRegistrationForm;

public interface UserService {
    public void save(User user);
    public User findByUsername(String username);
    public void register(UserRegistrationForm userRegistrationForm);
}
