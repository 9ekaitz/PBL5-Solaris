package eus.solaris.solaris.service.impl;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.exception.AvatarNotCreatedException;
import eus.solaris.solaris.form.UserInformationEditForm;
import eus.solaris.solaris.form.UserRegistrationForm;
import eus.solaris.solaris.repository.ImageRepository;
import eus.solaris.solaris.repository.UserRepository;
import eus.solaris.solaris.service.RoleService;
import eus.solaris.solaris.service.UserService;
import eus.solaris.solaris.util.Beam;

@Service
public class UserServiceImpl implements UserService {

    private static final int SIZE = 64;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
	PasswordEncoder passwordEncoder;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public User register(UserRegistrationForm userRegistrationForm) throws AvatarNotCreatedException {
        User user = modelMapper.map(userRegistrationForm, User.class);
        user.setRole(roleService.findByName("ROLE_USER"));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Beam b = new Beam();
        String svg = b.getAvatarBeam(user.getName()+user.getFirstSurname()+user.getSecondSurname(), SIZE, true);
        user = save(user);
        try {
            user.setAvatar(imageRepository.save(svg.getBytes(), "user_"+user.getId()+".svg"));
        } catch (Exception e) {
            throw new AvatarNotCreatedException(user.getUsername());
        }
        System.out.println();
        return save(user);
    }

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
    public User disableUser(User user) {
        user.setEnabled(false);
        return userRepository.save(user);
    }

    @Override
    public User editPassword(String newPassword, String oldPassword, User user) {

        if(BCrypt.checkpw(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            user = save(user);
        }     
        else{
            user = null;
        }

        return user;
    }

    @Override
    public User editUser(UserInformationEditForm form, User user) {
        user.setEmail(form.getEmail());
        user.setName(form.getName());
        user.setFirstSurname(form.getFirstSurname());
        user.setSecondSurname(form.getSecondSurname());
        return save(user);    
    }

    @Override
    public List<Address> getUserAddresses(User user) {

        user.setAddresses(userRepository.findAddressByUserId(user.getId()));

        return user.getAddresses();
    }

    @Override
    public List<PaymentMethod> getUserPaymentMethods(User user) {

        user.setPaymentMethods(userRepository.findPaymentMethodByUserId(user.getId()));
        
        return user.getPaymentMethods();
    }
}