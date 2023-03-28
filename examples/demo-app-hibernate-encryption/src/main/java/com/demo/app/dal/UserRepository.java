package com.demo.app.dal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findUserByName(String name);

    List<User> findUserByPhoneNumber(String phoneNumber);
}
