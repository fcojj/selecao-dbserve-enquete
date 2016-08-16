package com.github.enquete.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.enquete.model.User;
import com.github.enquete.repository.Users;

@Service
public class UserDaoService {

	@Autowired
	private Users users;
	
	public User getUserById(String username){
		User user  = new User();
		List<User> result = users.find(username);
		
		if(!result.isEmpty()){
			user = users.find(username).get(0);
		}

		return user;
	}
	
}
