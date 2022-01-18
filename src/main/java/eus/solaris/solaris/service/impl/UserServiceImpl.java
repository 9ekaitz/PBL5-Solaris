package eus.solaris.solaris.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserRegistrationForm;
import eus.solaris.solaris.repository.UserRepository;
import eus.solaris.solaris.service.RoleService;
import eus.solaris.solaris.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void register(UserRegistrationForm userRegistrationForm) {
        User user = modelMapper.map(userRegistrationForm, User.class);
        user.setRole(roleService.findByName("ROLE_USER"));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
    }
}
