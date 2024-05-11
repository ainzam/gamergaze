package com.gamegaze.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.gamegaze.domain.User;
import com.gamegaze.service.FollowService;
import com.gamegaze.service.UserService;

@Controller
public class FollowController {
    
    @Autowired
    private FollowService followService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/follow/userId")
    public String followUser(@PathVariable Long userId, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        User userToFollow = userService.getUserById(userId);
        
        followService.followUser(currentUser, userToFollow);
        
        return "redirect:/users/" + userId;
    }
    
    @PostMapping("/unfollow/userId")
    public String unfollowUser(@PathVariable Long userId, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        User userToUnfollow = userService.getUserById(userId);
        
        followService.unfollowUser(currentUser, userToUnfollow);
        
        return "redirect:/users/" + userId;
    }
}