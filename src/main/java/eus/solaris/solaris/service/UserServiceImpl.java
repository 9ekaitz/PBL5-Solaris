package eus.solaris.solaris.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserProfileCreateForm;
import eus.solaris.solaris.form.UserProfileUpdateForm;
import eus.solaris.solaris.form.UserRegistrationForm;
import eus.solaris.solaris.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Value("${solaris.pagination.pagesize}")
	private Integer pagesize;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;
    
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
        userRepository.save(user);
    }

    @Override
    public void update(Long id, UserProfileUpdateForm upuf) {
        User user = this.findById(id);
        user.setUsername(upuf.getUsername());
        user.setName(upuf.getName());
        user.setFirstSurname(upuf.getFirstSurname());
        user.setSecondSurname(upuf.getSecondSurname());
        user.setEmail(upuf.getEmail());
        user.setRole(roleService.findById(upuf.getRoleId()));
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
    public void create(UserProfileCreateForm upcf) {
        User user = new User();
        user.setUsername(upcf.getUsername());
        user.setName(upcf.getName());
        user.setFirstSurname(upcf.getFirstSurname());
        user.setSecondSurname(upcf.getSecondSurname());
        user.setEmail(upcf.getEmail());
        user.setPassword(passwordEncoder.encode(upcf.getPassword()));
        user.setRole(roleService.findById(upcf.getRoleId()));
        user.setEnabled(true);
        this.save(user);
    }

    public void register(UserRegistrationForm userRegistrationForm) {
        User user = modelMapper.map(userRegistrationForm, User.class);
        user.setRole(roleService.findByName("ROLE_USER"));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
    }
}
