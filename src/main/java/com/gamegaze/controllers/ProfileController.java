package com.gamegaze.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gamegaze.domain.Image;
import com.gamegaze.domain.User;
import com.gamegaze.service.ImageService;
import com.gamegaze.service.UserService;

import jakarta.annotation.Nullable;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ImageService imageService;
	
    @GetMapping("/profile")
    public ModelAndView profile(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user =(User) userService.loadUserByUsername(username);
		ModelAndView modelandview = new ModelAndView("profile");
		modelandview.addObject(user);
		return modelandview;
    }
	
    @PostMapping("/profile/edit")
    public String updateProfile(@RequestParam("email") String email,
    		@RequestParam("lastName") String lastName,
    		@RequestParam("username") String username,
    		@RequestParam("firstName") String firtsName,
            @Nullable @RequestParam("profileImage") MultipartFile profileImage, 
            @Nullable @RequestParam("bannerImage") MultipartFile bannerImage) throws IOException {
    	
    	User existingUser = (User) userService.loadUserByUsername(username);

        if (profileImage!= null) {
        	Image image = imageService.saveImage(profileImage);
        	existingUser.setProfileImage(image);
        }

        if (bannerImage!= null) {
        	Image image = imageService.saveImage(bannerImage);
        	existingUser.setBannerImage(image);
        }

        userService.updateUser(existingUser);

        return "redirect:/profile";
    }
    
}
