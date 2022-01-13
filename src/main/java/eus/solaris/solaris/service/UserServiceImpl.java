package eus.solaris.solaris.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserProfileForm;
import eus.solaris.solaris.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void disable(User user) {
        user.setEnabled(false);
    }

    @Override
    public void update(Long id, UserProfileForm upf) {
        User user = this.findById(id);
        user.setName(upf.getName());
        user.setFirstSurname(upf.getFirstSurname());
        user.setSecondSurname(upf.getSecondSurname());
        user.setAge(upf.getAge());
        user.setEmail(upf.getEmail());
        user.setRole(roleService.findById(upf.getRoleId()));
        this.save(user);
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        User user = this.findById(id);
        user.setPassword(passwordEncoder.encode(password));
        this.save(user);
    }

    @Override
    public List<User> findManageableUsers() {
        List<User> users = this.findAll();
        for (User user : users) {
            if (user.getRole().getName().equals("ROLE_USER") || 
                user.getEnabled().equals(false)) {
                users.remove(user);
            }
        }
        return users;
    }
}
