package com.gamegaze.service;

import org.springframework.stereotype.Service;

import com.gamegaze.controllers.RegistrationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {

	public String register(RegistrationRequest request) {
		return "works";
	}

}
