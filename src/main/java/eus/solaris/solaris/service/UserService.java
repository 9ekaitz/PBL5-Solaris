package eus.solaris.solaris.service;

import java.util.List;

import org.springframework.beans.support.PagedListHolder;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserProfileCreateForm;
import eus.solaris.solaris.form.UserProfileUpdateForm;

public interface UserService {
    public void save(User user);
    public User findByUsername(String username);
    public List<User> findAll();
    public User findById(Long id);
    public void update(Long id, UserProfileUpdateForm upuf);
    public void updateUserPassword(Long id, String password);
    public void disable(User user);
    public List<User> findManageableUsers();
    public void create(UserProfileCreateForm upcf);
    public PagedListHolder<User> getPagesFromUsersList(List<User> users);
}
