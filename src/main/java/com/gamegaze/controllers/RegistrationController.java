package com.gamegaze.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gamegaze.service.RegistrationService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class RegistrationController {

	private final RegistrationService registrationService;
	
	@GetMapping(value = "/registration")
	public String registration() {
		return "registrationForm";
	}
	
    @PostMapping("/registration")
    public String register(@RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password) {
        registrationService.register(firstName, lastName, email, password);
        return "redirect:/login";
    }

}
