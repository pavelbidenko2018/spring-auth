package com.pbidenko.springauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pbidenko.springauth.entity.UsrProfile;

@Repository
public interface UsrProfileRepo extends CrudRepository<UsrProfile, Integer>{
		
	public Iterable<UsrProfile> findAll();
	public Optional<UsrProfile> findById(int id);
	
	@Modifying
	@Query(value = "update UsrProfile u set userpic =:photo where authUser = 1")
	public int updatePhoto(@Param("photo") String photo);

}
