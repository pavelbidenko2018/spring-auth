package com.pbidenko.springauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AppUserDetailsService appUserDetailsService;

	@Autowired
	LogoutHandler logoutHandler;

	@Autowired
	AuthenticationSuccessHandler successHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(appUserDetailsService);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/registration", "/login", "/users", "/addUser", "/js/**", "/css/*", "/images/*",
						"/fonts/*", "/admin/**", "/updateArticle", "/addArticle", "/sendEmail", "/forgetPassword",
						"/reset-password")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login")
				.successHandler(successHandler).failureUrl("/#login_error").permitAll().and().logout()
				.addLogoutHandler(logoutHandler).permitAll()

				.and().csrf().disable();

	}

	@Bean
	protected BCryptPasswordEncoder getPasswordEncoder() {

		return new BCryptPasswordEncoder();

	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(appUserDetailsService);
		auth.setPasswordEncoder(getPasswordEncoder());
		return auth;
	}
}