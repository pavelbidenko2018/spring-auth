package com.pbidenko.springauth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pbidenko.springauth.entity.UsrProfile;

@Repository
public interface UsrProfileRepo extends CrudRepository<UsrProfile, Long>{
		
	public Iterable<UsrProfile> findAll();
	public Optional<UsrProfile> findById(long id);

}
