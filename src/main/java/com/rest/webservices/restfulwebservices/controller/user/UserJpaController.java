package com.rest.webservices.restfulwebservices.controller.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.webservices.restfulwebservices.entity.user.Post;
import com.rest.webservices.restfulwebservices.entity.user.User;
import com.rest.webservices.restfulwebservices.exception.user.UserNotFoundException;
import com.rest.webservices.restfulwebservices.repository.user.UserRepository;
import com.rest.webservices.restfulwebservices.repository.user.posts.PostRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
//	public UserJpaController(UserRepository userRepository) {
//		super();
//		this.userRepository = userRepository;
//	}

	@GetMapping("/jpa/users")
	public List<User> getAllUsers(){
	   	return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> getUser(@PathVariable String id){
	   	Optional<User> user = userRepository.findById(id);
	   	if(user.isEmpty()) {
	   		throw new UserNotFoundException("id :"+id);
	   	}
	   	EntityModel<User> entityModel = EntityModel.of(user.get());
	   	
	   	WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
	   	entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
	   	User saveUser = userRepository.save(user);
	   	URI location = ServletUriComponentsBuilder.fromCurrentRequest().
	   			path("/{id}").buildAndExpand(saveUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable String id){
		userRepository.deleteById(id);
	}
	
	@GetMapping("/jpa/users/posts")
	public List<Post> getAllPosts(){
	   	return postRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllPostsForUser(@PathVariable String id){
		Optional<User> user = userRepository.findById(id);
	   	if(user.isEmpty()) {
	   		throw new UserNotFoundException("id :"+id);
	   	}
	   return user.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Post> createPostForUser(@PathVariable String id,
			@Valid @RequestBody Post post){
	   Optional<User> user = userRepository.findById(id);
	   if(user.isEmpty()) {
	   	  throw new UserNotFoundException("id :"+id);
	   }
	   post.setUser(user.get());
	   Post savedPost = postRepository.save(post);
	   URI location = ServletUriComponentsBuilder.fromCurrentRequest().
	   			path("/{postid}").buildAndExpand(savedPost.getId()).toUri();
	   return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users/{id}/posts/{postid}")
	public Post retrievePostForUser(@PathVariable String id,
			@PathVariable Integer postid){
		Optional<Post> post = postRepository.findById(postid);
	    return post.get();
	}
}
