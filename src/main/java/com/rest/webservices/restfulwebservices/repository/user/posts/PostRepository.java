package com.rest.webservices.restfulwebservices.repository.user.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.webservices.restfulwebservices.entity.user.Post;

public interface PostRepository extends JpaRepository<Post,Integer>{

}
