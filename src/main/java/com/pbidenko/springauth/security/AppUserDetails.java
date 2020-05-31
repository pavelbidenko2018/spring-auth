package com.pbidenko.springauth.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pbidenko.springauth.entity.Role;
import com.pbidenko.springauth.entity.Usr;
import com.pbidenko.springauth.repository.UsrRepo;

public class AppUserDetails implements UserDetails {

	@Autowired
	UsrRepo usrRepo;

	Usr usr;

	private static final long serialVersionUID = 1L;

	public AppUserDetails(String userName) {
		super();
		this.usr = usrRepo.findByEmail(userName).get();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(Role.ADMIN, Role.USER).stream().map(n -> new SimpleGrantedAuthority(n.name()))
				.collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {
		return usr.getPwd();
	}

	@Override
	public String getUsername() {
		return usr.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
