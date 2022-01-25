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

	@Value("${solaris.security.remember-me.key}")
	private String rememberMeKey;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
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
				.deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher(logoutUrl))
				.logoutSuccessUrl(loginUrl)
				.and()
				.rememberMe()
				.key(rememberMeKey)
				.userDetailsService(userDetailsService);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
}