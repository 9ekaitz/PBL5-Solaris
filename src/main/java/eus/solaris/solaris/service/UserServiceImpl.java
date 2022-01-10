package eus.solaris.solaris.service;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void editPassword(String newPassword, Authentication authentication) {
        User user = findByUsername(authentication.getName());
        user.setPassword(newPassword);
        userRepository.save(user);        
    }
}
