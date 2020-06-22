package com.pbidenko.springauth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pbidenko.springauth.entity.Usr;

@Repository
public interface UsrRepo extends CrudRepository<Usr, Integer> {

	public Optional<Usr> findByEmail(String email);

}
