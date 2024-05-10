package com.gamegaze.controllers;

import java.io.IOException;
import java.util.ArrayList;
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
import com.gamegaze.service.PublicationService;
import com.gamegaze.service.UserService;

import jakarta.annotation.Nullable;



@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PublicationService 	publicationService;
	
	@Autowired
	private ImageService imageService;
	
	private User currentUser;
	
    @GetMapping("/home")
    public Model home(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user =(User) userService.loadUserByUsername(username);
		model.addAttribute(user);
		
		List<Publication> publications = userService.getPublicationsByUser(user);
		model.addAttribute("publications", publications);
		return model;
    }
    
    @PostMapping("/createPublication")
    public String createPost(@RequestParam("textContent") String textContent, @RequestParam("images") MultipartFile[] images) throws IOException {
        setCurrentUser();
        Publication publication = new Publication();
        publication.setTextContent(textContent);
        publication.setUser(currentUser);

        List<Image> imagespost = new ArrayList<>();

        if (images!= null && images.length > 0) {
            for (MultipartFile image : images) {
                try {
                    Image imgsave = imageService.saveImage(image);
                    imagespost.add(imgsave);
                    System.out.println("Image saved successfully: {}");
                } catch (Exception e) {
                	System.out.println("Error saving image: {}");
                }
            }
        } else {
        	System.out.println("No images provided");
        }

        publication.setImages(imagespost);
        publicationService.savePublication(publication);
        return "redirect:/home";
    }
    
    private void setCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        currentUser = (User) userService.loadUserByUsername(username);
    }
    
	
}
