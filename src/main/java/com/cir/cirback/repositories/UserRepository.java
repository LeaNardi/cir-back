package com.cir.cirback.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cir.cirback.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}