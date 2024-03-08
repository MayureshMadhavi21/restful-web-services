package com.rest.webservices.restfulwebservices.dao.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.rest.webservices.restfulwebservices.entity.user.User;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<User>();
    private static int userCount = 0;
	static {
		users.add(new User(String.valueOf(++userCount),"Adam", LocalDate.now().minusYears(30)));
		users.add(new User(String.valueOf(++userCount), "Jim", LocalDate.now().minusYears(20)));
		users.add(new User(String.valueOf(++userCount), "Eve", LocalDate.now().minusYears(25)));
	}

	public List<User> getAllUsers() {
		return users;
	}

	public User saveUser(User user) {
		user.setId(Integer.toString(++userCount));
		users.add(user);
		return user;
	}
	
	public User getUser(String id) {
		
//		User user = new User(); 
//		for(User user1 : users) {
//			if(id.equals(user1.getId())){
//				user = user1;
//			}
//		}
//		return user;
		
		//functional approach
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);
	}
	
	public void deleteUser(String id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		users.removeIf(predicate);
	}
}
