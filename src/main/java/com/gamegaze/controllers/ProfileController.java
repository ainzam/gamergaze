package com.gamegaze.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String updateProfile(@ModelAttribute User user, BindingResult result,
            @Nullable @RequestParam("profileImage") MultipartFile profileImage, 
            @Nullable @RequestParam("bannerImage") MultipartFile bannerImage) throws IOException {
        if (result.hasErrors()) {
            return "profile";
        }

        if (profileImage!= null) {
        	Image image = imageService.saveImage(profileImage);
        	user.setProfileImage(image);
        }

        if (bannerImage!= null) {
        	Image image = imageService.saveImage(bannerImage);
        	user.setBannerImage(image);
        }

        userService.updateUser(user,profileImage,bannerImage);

        return "redirect:/profile";
    }
    
}
