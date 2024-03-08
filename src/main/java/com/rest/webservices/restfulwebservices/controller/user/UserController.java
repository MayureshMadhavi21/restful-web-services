package com.rest.webservices.restfulwebservices.controller.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.webservices.restfulwebservices.dao.user.UserDaoService;
import com.rest.webservices.restfulwebservices.entity.user.User;
import com.rest.webservices.restfulwebservices.exception.user.UserNotFoundException;

import jakarta.validation.Valid;

@RestController
public class UserController {
    
	@Autowired
	private UserDaoService userDaoService;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
	   	return userDaoService.getAllUsers();
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> getUser(@PathVariable String id){
	   	User user = userDaoService.getUser(id);
	   	if(user==null) {
	   		throw new UserNotFoundException("id :"+id);
	   	}
	   	EntityModel<User> entityModel = EntityModel.of(user);
	   	
	   	WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
	   	entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
	   	User saveUser = userDaoService.saveUser(user);
	   	URI location = ServletUriComponentsBuilder.fromCurrentRequest().
	   			path("/{id}").buildAndExpand(saveUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable String id){
	  userDaoService.deleteUser(id);
	}
}
