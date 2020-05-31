package com.pbidenko.springauth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pbidenko.springauth.entity.Usr;

public interface UsrRepo extends CrudRepository<Usr, Integer> {

	public Optional<Usr> findByEmail(String email);

}
