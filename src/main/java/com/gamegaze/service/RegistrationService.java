package com.gamegaze.service;

import org.springframework.stereotype.Service;

import com.gamegaze.domain.User;
import com.gamegaze.domain.UserRole;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {
	
	private final UserService userService;

	public String register(String firstName, String lastName,String email,String password) {
		return userService.signUpUser(new User(firstName,lastName,email,password,UserRole.ROLE_USER));
	}

}
