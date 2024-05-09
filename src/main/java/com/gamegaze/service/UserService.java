package com.gamegaze.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamegaze.domain.Image;
import com.gamegaze.domain.User;
import com.gamegaze.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private ImageService imageService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el username: " + username));

    }
    
    public String signUpUser(User user) {
    	boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
    	
    	if(userExists) {
    		throw new IllegalStateException("email already taken");
    	}
    	String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
    	
    	user.setPassword(encodedPassword);
    	
    	user.setProfileImage(imageService.getDefaultProfileImage());
    	user.setBannerImage(imageService.getDefaultProfileBanner());
    	
    	userRepository.save(user); 
    	
    	return "it works";
    }

}
