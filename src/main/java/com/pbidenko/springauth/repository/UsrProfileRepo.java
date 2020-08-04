package com.pbidenko.springauth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pbidenko.springauth.entity.Usr;
import com.pbidenko.springauth.entity.UsrProfile;

@Repository
public interface UsrProfileRepo extends JpaRepository<UsrProfile, Integer> {

	public List<UsrProfile> findAll();

	public Optional<UsrProfile> findById(int id);
	
	public Optional<UsrProfile> findByAuthUser(Usr usr);

	@Modifying
	@Query(value = "update UsrProfile u set userpic =:photo where authUser = :id")
	public int updatePhoto(@Param("photo") String photo, @Param("id") Usr id);

	
}
