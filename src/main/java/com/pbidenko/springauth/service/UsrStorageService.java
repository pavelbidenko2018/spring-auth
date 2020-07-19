package com.pbidenko.springauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbidenko.springauth.entity.Usr;
import com.pbidenko.springauth.exception.UsrNotFoundException;
import com.pbidenko.springauth.repository.UsrRepo;

@Service
public class UsrStorageService {
	
	@Autowired
	UsrRepo usrRepo;
	
	public void save(Usr usr) {
		usrRepo.save(usr);
	}
	
	public Usr findById(String id) {
		
		return usrRepo.findById(Integer.valueOf(id)).orElseThrow(()->new UsrNotFoundException(id));
	}

	public Usr findByEmail(String username) {
		
		return usrRepo.findByEmail(username).orElseThrow(()->new UsrNotFoundException(username));		
		
	}
	

}
