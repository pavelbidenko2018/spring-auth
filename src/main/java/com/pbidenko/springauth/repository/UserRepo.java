package com.pbidenko.springauth.repository;

import org.springframework.data.repository.CrudRepository;

import com.pbidenko.springauth.entity.User;

public interface UserRepo extends CrudRepository<User, Integer>{

}
