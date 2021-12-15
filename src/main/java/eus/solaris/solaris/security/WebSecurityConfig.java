package eus.solaris.solaris.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import eus.solaris.solaris.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Value("${solaris.web.login-url}")
	private String loginUrl;

	@Value("${solaris.web.login-failed-url}")
	private String loginFailedUrl;

	@Value("${solaris.web.logout-url}")
	private String logoutUrl;

	@Value("${solaris.web.home-url}")
	private String homeUrl;

	@Autowired
	private UserService userService;

	private String test1;
	private String test2;
	private String test3;
	private String test4;
	private String test5;
	private String test6;
	private String test7;
	private String test8;
	private String test9;
	private String test10;
	private String test11;
	private String test12;
	private String test13;
	private String test14;
	private String test15;
	private String test16;
	private String test17;
	private String test18;
	private String test19;
	private String test20;
	private String test21;
	private String test22;
	private String test23;
	private String test24;
	private String test25;
	private String test26;
	private String test27;
	private String test28;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.requiresChannel(channel -> channel.anyRequest().requiresSecure()).authorizeRequests()
			.anyRequest().permitAll()
			.and()
			.formLogin()
				.loginPage(loginUrl)
				.defaultSuccessUrl(homeUrl)
				.failureUrl(loginFailedUrl)
				.loginProcessingUrl(loginUrl).permitAll()
			.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher(logoutUrl))
				.logoutSuccessUrl(loginUrl);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
}