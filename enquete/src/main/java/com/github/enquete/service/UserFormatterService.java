package com.github.enquete.service;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.github.enquete.model.User;

@Component
public class UserFormatterService implements Formatter<User>{
	
    @Autowired
	private UserDaoService userDaoService;
	
	public UserFormatterService() {
		
	}
	
	@Override
	public String print(User user, Locale arg1) {
		return user.getUsername();
	}

	@Override
	public User parse(String username, Locale arg1) throws ParseException {
		User user = userDaoService.getUserById(username);
		
		return user;
	}

	
   
    

}