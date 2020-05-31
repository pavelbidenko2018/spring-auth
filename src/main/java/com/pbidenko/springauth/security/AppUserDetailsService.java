package com.pbidenko.springauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pbidenko.springauth.entity.Usr;
import com.pbidenko.springauth.repository.UsrRepo;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	UsrRepo usrRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Usr usr = usrRepo.findByEmail(email).orElse(null);
		System.out.println(usr);
		
		return new AppUserDetails(usr);
	}

}
