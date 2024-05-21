package com.gamegaze.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.gamegaze.domain.User;
import com.gamegaze.service.FollowService;
import com.gamegaze.service.UserService;

@Controller
public class FollowController {
    
	private User currentUser;
	
    @Autowired
    private FollowService followService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/follow/{username}")
    public String followUser(@PathVariable String username) {
        User userToFollow = userService.getUserByUsername(username);
        setCurrentUser();
        
        followService.followUser(currentUser, userToFollow);
        
        return "redirect:/profile/" + userToFollow.getUsername();
    }
    
    @GetMapping("/unfollow/{username}")
    public String unfollowUser(@PathVariable String username) {
    	setCurrentUser();
        User userToUnfollow = userService.getUserByUsername(username);
        
        followService.unfollowUser(currentUser, userToUnfollow);
        
        return "redirect:/profile/" + userToUnfollow.getUsername();
    }
    
    private void setCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        currentUser = (User) userService.loadUserByUsername(username);
    }
}