package eus.solaris.solaris.security;

import org.springframework.beans.factory.annotation.Autowired;
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
		User user = userRepository.findByUsernameIgnoreCase(username);
		UserDetails userDetails = null;
		
		if(user != null) {
			userDetails = new CustomUserDetails(user);
		}
		else throw new UsernameNotFoundException("Username not found");

		return userDetails;
	}

}
