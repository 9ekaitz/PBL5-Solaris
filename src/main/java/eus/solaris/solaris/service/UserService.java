package eus.solaris.solaris.service;

import eus.solaris.solaris.domain.User;

public interface UserService {
    public void save(User user);
    public User findByUsername(String username);
}
