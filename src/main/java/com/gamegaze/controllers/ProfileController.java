package com.gamegaze.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gamegaze.domain.Image;
import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;
import com.gamegaze.service.ImageService;
import com.gamegaze.service.PublicationService;
import com.gamegaze.service.UserService;

import jakarta.annotation.Nullable;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PublicationService publicationService;
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping("/profile/{username}")
	public ModelAndView profile(@PathVariable String username) {
	    User userInPath = userService.getUserByUsername(username);

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    User currentUser = (User) userService.loadUserByUsername(currentUsername);

	    List<Publication> publications = new ArrayList<>();
	    ModelAndView modelAndView = new ModelAndView();

	    if (userInPath != null && !userInPath.getUsername().equals(currentUser.getUsername())) {
	    	modelAndView.setViewName("profile");
	    	modelAndView.addObject("user",userInPath);
	        publications.addAll(publicationService.getPublicationsByUser(userInPath));
	    } else if (userInPath == null) {
	        modelAndView.setViewName("userNotFound");
	        return modelAndView;
	    } else {
	        publications.addAll(publicationService.getPublicationsByUser(currentUser));
	    }
    	modelAndView.addObject("currentuser",currentUser);
	    modelAndView.addObject("publications", publications);
	    modelAndView.setViewName("profile");
	    return modelAndView;
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
        
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    User currentUser = (User) userService.loadUserByUsername(currentUsername);

        return "redirect:/profile/" + currentUser.getUsername();
    }
    
}
