package eus.solaris.solaris.service.impl;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserProfileCreateForm;
import eus.solaris.solaris.form.UserProfileUpdateForm;
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

    @Value("${solaris.pagination.users.pagesize}")
	private Integer pagesize;
    private static final long serialVersionUID = 4889944577388711145L;
    private static final int SIZE = 32;

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
        String svg = b.getAvatarBeam(user.getName() + user.getFirstSurname() + user.getSecondSurname(), SIZE, true);
        user = save(user);
        try {
            user.setAvatar(imageRepository.save(svg.getBytes(), "user_" + user.getId() + ".svg"));
        } catch (Exception e) {
            throw new AvatarNotCreatedException(user.getUsername());
        }
        return save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
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
    public Boolean disable(User user) {
        try {
            user.setEnabled(false);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    @Override
    public Boolean update(Long id, UserProfileUpdateForm upuf) {
        User user = this.findById(id);
        if (user != null) {
            user.setUsername(upuf.getUsername());
            user.setName(upuf.getName());
            user.setFirstSurname(upuf.getFirstSurname());
            user.setSecondSurname(upuf.getSecondSurname());
            user.setEmail(upuf.getEmail());
            user.setRole(roleService.findById(upuf.getRoleId()));
            this.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateUserPassword(Long id, String password) {
        try {
            User user = this.findById(id);
            user.setPassword(passwordEncoder.encode(password));
            this.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<User> findManageableUsers() {
        return userRepository.findAllByRoleNameNotAndEnabled("ROLE_USER", true);
    }

    @Override
    public PagedListHolder<User> getPagesFromUsersList(List<User> users) {
        PagedListHolder<User> pages = new PagedListHolder<>(users);
        pages.setPageSize(pagesize);
        pages.setPage(0);
        return pages;
    }

    @Override
    public Boolean create(UserProfileCreateForm upcf) {
        try {
            User user = new User();
            user.setUsername(upcf.getUsername());
            user.setName(upcf.getName());
            user.setFirstSurname(upcf.getFirstSurname());
            user.setSecondSurname(upcf.getSecondSurname());
            user.setEmail(upcf.getEmail());
            user.setPassword(passwordEncoder.encode(upcf.getNewPassword()));
            user.setRole(roleService.findById(upcf.getRoleId()));
            user.setEnabled(true);
            this.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }

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

        if (BCrypt.checkpw(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user = save(user);
        } else {
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

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}