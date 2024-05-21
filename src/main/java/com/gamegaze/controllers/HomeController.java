package com.gamegaze.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import com.gamegaze.domain.Image;
import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;
import com.gamegaze.service.FollowService;
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
	
	@Autowired
	private FollowService followService;
	
	
	private User currentUser;
	
	
    @GetMapping("/home")
    public Model home(Model model) {
    	setCurrentUser();
		model.addAttribute("currentuser",currentUser);
		
		List<Publication> publications = publicationService.getPublicationsByUser(currentUser);
		
		List<User> usersFollowed = userService.getFollowedUsersByUser(currentUser);
		
		List<Publication> allPublications = new ArrayList<>();
		
		for(User user : usersFollowed) {
			List<Publication> usersFollowedPublications = publicationService.getPublicationsByUser(user);
			allPublications.addAll(usersFollowedPublications);
		}
		allPublications.addAll(publications);
		allPublications.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));
		model.addAttribute("publications", allPublications);
		return model;
    }
    
    @PostMapping("/createPublication")
    public String createPost(@RequestParam("textContent") String textContent,@Nullable @RequestParam("images") MultipartFile[] images) throws IOException {
        setCurrentUser();
        Publication publication = new Publication();
        publication.setTextContent(textContent);
        publication.setUser(currentUser);
        publication.setCreatedAt(new Date());

        List<Image> imagespost = new ArrayList<>();

        if (images!= null && images.length > 0) {
            for (MultipartFile image : images) {
                try {
                	if (!image.isEmpty()) {
	                    Image imgsave = imageService.saveImage(image);
	                    imagespost.add(imgsave);
	                    publication.setImages(imagespost);
	                    System.out.println("Image saved successfully: {}");
                	}
                } catch (Exception e) {
                	System.out.println("Error saving image: {}");
                }
            }
        } else {
        	System.out.println("No images provided");
        }

 
        publicationService.savePublication(publication);
        return "redirect:/home";
    }
    
    private void setCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        currentUser = (User) userService.loadUserByUsername(username);
    }
    
    @PostMapping("/searchUsername")
    public ModelAndView createPost(@RequestParam("username") String username) {
    	ModelAndView modelandview = new ModelAndView();
    	setCurrentUser();
    	modelandview.addObject("currentuser",currentUser);
    	User userInPath = userService.getUserByUsername(username);
    	
    	if(userInPath != null) {
    		modelandview.setViewName("searchUsername");
    		modelandview.addObject("userfound",userInPath);
    		boolean isFollowing = followService.isFollowing(currentUser, userInPath);
    	    modelandview.addObject("isFollowing", isFollowing);
    	}else {
    		modelandview.setViewName("userNotFound");
    	}

    	return modelandview;
    }
    
    @GetMapping("/following")
    public String following(Model model) {
		setCurrentUser();
		List<User> users = userService.getFollowedUsersByUser(currentUser);
		model.addAttribute("users",users);
		model.addAttribute("currentuser",currentUser);
		
		return "following";
    }
	
}
