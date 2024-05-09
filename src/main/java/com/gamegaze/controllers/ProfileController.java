package com.gamegaze.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gamegaze.domain.User;
import com.gamegaze.service.UserService;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;
	
    @GetMapping("/profile")
    public ModelAndView profile(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user =(User) userService.loadUserByUsername(username);
		ModelAndView modelandview = new ModelAndView("profile");
		modelandview.addObject(user);
		return modelandview;
    }
	
}
