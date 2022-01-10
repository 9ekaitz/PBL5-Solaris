package eus.solaris.solaris.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
	PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        return userRepository.save(user);
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
    public boolean editPassword(String newPassword, String oldPassword, Authentication authentication) {
        Boolean result = false;
        User user = findByUsername(authentication.getName());

        if(BCrypt.checkpw(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            save(user);
            result = true;
        }     

        return result;
    }

    @Override
    public boolean editUser(String name, String firstSurname, String secondSurname, String email, Authentication authentication) {
        Boolean result = false;
        User user = findByUsername(authentication.getName());
        user.setEmail(email);
        user.setName(name);
        user.setFirstSurname(firstSurname);
        user.setSecondSurname(secondSurname);
        User returnedUser = save(user);
        if(returnedUser != null) result = true;

        return result;
    
    }
}
