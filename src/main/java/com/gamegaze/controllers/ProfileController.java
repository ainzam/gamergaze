package com.gamegaze.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.gamegaze.domain.Image;
import com.gamegaze.domain.Publication;
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
    public Model profile(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user =(User) userService.loadUserByUsername(username);
		model.addAttribute(user);
		
		List<Publication> publications = userService.getPublicationsByUser(user);
		model.addAttribute("publications", publications);
		return model;
    }
	
    @PostMapping("/profile/edit")
    public String updateProfile(@RequestParam("email") String email,
    		@RequestParam("lastName") String lastName,
    		@RequestParam("username") String username,
    		@RequestParam("firstName") String firstName,
            @Nullable @RequestParam("profileImage") MultipartFile profileImage, 
            @Nullable @RequestParam("bannerImage") MultipartFile bannerImage) throws IOException {
    	
    	User existingUser = (User) userService.loadUserByUsername(username);

    	if (email!= null &&!email.isEmpty()) {
            existingUser.setEmail(email);
        }
        if (lastName!= null &&!lastName.isEmpty()) {
            existingUser.setLastName(lastName);
        }
        if (firstName!= null &&!firstName.isEmpty()) {
            existingUser.setFirstName(firstName);
        }

        if (!profileImage.isEmpty()) {
            Image image = imageService.saveImage(profileImage);
            existingUser.setProfileImage(image);
        }

        if (!bannerImage.isEmpty()) {
            Image image = imageService.saveImage(bannerImage);
            existingUser.setBannerImage(image);
        }

        userService.updateUser(existingUser);

        return "redirect:/profile";
    }
    
}
