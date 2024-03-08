package com.rest.webservices.restfulwebservices.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.webservices.restfulwebservices.entity.user.User;

public interface UserRepository extends JpaRepository<User,String>{

}
