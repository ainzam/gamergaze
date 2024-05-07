package com.gamegaze.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gamegaze.domain.User;
import com.gamegaze.service.UserService;



@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
    @GetMapping("/home")
    public Model home(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user =(User) userService.loadUserByUsername(username);
		model.addAttribute(user);
		return model;
    }
    
    @PostMapping(value = "/createPost")
    public String createPost() {
    	return null;
    }
    
    @GetMapping(value="/imagesPruebas")
	public String imagesPruebas(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user =(User) userService.loadUserByUsername(username);
		model.addAttribute(user);
		return "profile";
	}
	
}
