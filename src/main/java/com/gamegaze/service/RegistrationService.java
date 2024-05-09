package com.gamegaze.service;

import org.springframework.stereotype.Service;

import com.gamegaze.domain.User;
import com.gamegaze.domain.UserRole;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {
	
	private final UserService userService;

	public String register(User user) {
		user.setRole(UserRole.ROLE_USER);
		return userService.signUpUser(user);
	}
}
