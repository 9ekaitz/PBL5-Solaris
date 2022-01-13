package eus.solaris.solaris.service;

import java.util.List;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserProfileForm;

public interface UserService {
    public void save(User user);
    public User findByUsername(String username);
    public List<User> findAll();
    public User findById(Long id);
    public void update(Long id, UserProfileForm upf);
    public void updateUserPassword(Long id, String password);
    public void disable(User user);
    public List<User> findManageableUsers();
}
