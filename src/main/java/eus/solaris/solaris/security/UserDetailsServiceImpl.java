package eus.solaris.solaris.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		UserBuilder userBuilder = null;
		
		if(user != null) {
			userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
			userBuilder.disabled(false);
			userBuilder.password(user.getPassword());
			userBuilder.authorities(new SimpleGrantedAuthority((user.getRole() != null) ? user.getRole().getName() : "ADMIN"));
		}
		else throw new UsernameNotFoundException("Username not found");

		return userBuilder.build();
	}

}
