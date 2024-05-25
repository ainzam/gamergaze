package com.gamegaze.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamegaze.domain.Follow;
import com.gamegaze.domain.User;
import com.gamegaze.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{


    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ImageService imageService;
    
    @Autowired
    private FollowService followService;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el username: " + username));

    }
    
    public String signUpUser(User user) {
        boolean emailExists = userRepository.findByEmail(user.getEmail()).isPresent();
        boolean usernameExists = userRepository.findByUsername(user.getUsername()).isPresent();
    	
        if (emailExists) {
            throw new IllegalStateException("Email already taken");
        }

        if (usernameExists) {
            throw new IllegalStateException("Username already taken");
        }
    	String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
    	
    	user.setPassword(encodedPassword);
    	
    	user.setProfileImage(imageService.getDefaultProfileImage());
    	user.setBannerImage(imageService.getDefaultProfileBanner());
    	
    	userRepository.save(user); 
    	return "it works";
    }
    
    public void updateUser(User user) throws IOException {
        User existingUser = userRepository.findByUsername(user.getUsername())
               .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setUsername(user.getUsername());
        userRepository.save(existingUser);
    }
    
    public User getUserById(Long id){
    	return userRepository.findById(id).orElse(null);
    }
    

    public List<User> getFollowedUsersByUser(User currentUser){
    	
        List<User> followedUsers = new ArrayList<>();
        
        List<Follow> followed = followService.getAllFollowedByUser(currentUser);
        

        for (Follow follow : followed) {

            if (follow.getFollowed() != null) {
                followedUsers.add(follow.getFollowed());
            }
        }
        return followedUsers;
    	
    }
    
    public User getUserByUsername (String username) {
    	return userRepository.findByUsername(username).orElse(null);
    }
    
    

}
