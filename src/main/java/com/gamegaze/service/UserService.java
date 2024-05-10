package com.gamegaze.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    
    public void updateUser(User user, MultipartFile profileImage, MultipartFile bannerImage) throws IOException {
    	
        User existingUser = userRepository.findByUsername(user.getUsername())
               .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        if (profileImage!= null) {
            Image profileImageEntity = new Image();
            profileImageEntity.setData(profileImage.getBytes());
            existingUser.setProfileImage(profileImageEntity);
        }

        // Update the user's banner image
        if (bannerImage!= null) {
            Image bannerImageEntity = new Image();
            bannerImageEntity.setData(bannerImage.getBytes());
            existingUser.setBannerImage(bannerImageEntity);
        }


        userRepository.save(existingUser);
    }
    

}
