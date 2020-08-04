package com.pbidenko.springauth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pbidenko.springauth.entity.PasswordResetToken;

@Repository
public interface ForgetPasswordRepo extends CrudRepository<PasswordResetToken, Integer> {
	public PasswordResetToken findByToken(String token);	
}
