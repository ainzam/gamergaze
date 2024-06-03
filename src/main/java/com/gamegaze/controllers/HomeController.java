package com.gamegaze.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gamegaze.domain.ContactMessage;
import com.gamegaze.domain.ContactReason;
import com.gamegaze.domain.Game;
import com.gamegaze.domain.Image;
import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;
import com.gamegaze.service.ContactMessageService;
import com.gamegaze.service.FollowService;
import com.gamegaze.service.GameService;
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
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private ContactMessageService contactMessageService;
	
	
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
		
		List<Game> games = gameService.getAllGames();
		model.addAttribute("games",games);
		Collections.shuffle(games);
		List<Game> randomGames = games.stream().limit(3).toList();
		model.addAttribute("randomGames",randomGames);
		model.addAttribute("publications", allPublications);
		return model;
    }
    
    @GetMapping("/home/admin")
    public ModelAndView adminHome(Model model) {
    	ModelAndView modelAndView = new ModelAndView("adminConsole");
    	setCurrentUser();
    	modelAndView.addObject("currentuser",currentUser);
    	
    	List<Publication> publications = publicationService.getAllPublications();
    	modelAndView.addObject("publications", publications);
    	
    	List<User> users = userService.getAllUsers();
    	modelAndView.addObject("users", users);
    	
    	List<ContactMessage> messages = contactMessageService.getAllContactMessages();
    	modelAndView.addObject("messages", messages);
    	
        long bugCount = messages.stream().filter(m -> m.getReason() == ContactReason.BUG).count();
        long recommendationCount = messages.stream().filter(m -> m.getReason() == ContactReason.GAME_RECOMMENDATION).count();
        
        modelAndView.addObject("bugCount", bugCount);
        modelAndView.addObject("recommendationCount", recommendationCount);
    	
    	return modelAndView;
    }
    
    @PostMapping("/home/admin/deleteUser")
    public String deleteUser(@RequestParam("userId") Long userId) {
    	User user = userService.getUserById(userId);
        userService.deleteUser(user);
        return "redirect:/home/admin";
    }

    @PostMapping("/home/admin/deletePublication")
    public String deletePublication(@RequestParam("publicationId") Long publicationId) {
    	Publication publication = publicationService.getPublicationById(publicationId);
        publicationService.deletePublication(publication);
        return "redirect:/home/admin";
    }
    
    @GetMapping("/home/admin/contactMessages/BUG")
    public String viewBugMessages(Model model) {
        List<ContactMessage> allMessages = contactMessageService.getAllContactMessages();
        List<ContactMessage> bugMessages = new ArrayList<>();
        for (ContactMessage message : allMessages) {
            if (message.getReason() == ContactReason.BUG) {
                bugMessages.add(message);
            }
        }
    	setCurrentUser();
    	model.addAttribute("currentuser",currentUser);
        model.addAttribute("messages", bugMessages);
        return "viewBugMessages"; 
    }

    @PostMapping("/deletePublication")
    public String deleteOwnPublication(@RequestParam("publicationId") Long publicationId) {
        setCurrentUser();
        Publication publication = publicationService.getPublicationById(publicationId);
        
        if (publication.getUser().getId().equals(currentUser.getId())) {
            publicationService.deletePublication(publication);
        }
        
        return "redirect:/profile";
    }
    
    @GetMapping("/home/admin/contactMessages/GAME_RECOMMENDATION")
    public String viewRecommendationMessages(Model model) {
        List<ContactMessage> allMessages = contactMessageService.getAllContactMessages();
        List<ContactMessage> recommendationMessages = new ArrayList<>();
        for (ContactMessage message : allMessages) {
            if (message.getReason() == ContactReason.GAME_RECOMMENDATION) {
                recommendationMessages.add(message);
            }
        }
    	setCurrentUser();
    	model.addAttribute("currentuser",currentUser);
        model.addAttribute("messages", recommendationMessages);
        return "viewRecommendationMessages";
    }
    
    @PostMapping("/home/admin/suspendUser")
    public ResponseEntity<?> suspendUser(@RequestBody Map<String, String> request) throws ParseException, IOException {
        Long userId = Long.parseLong(request.get("userId"));
        String suspendUntilStr = request.get("suspendUntil");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date suspendUntil = dateFormat.parse(suspendUntilStr);
        Date now = new Date();

        if (suspendUntil.before(now)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Suspend until date cannot be in the past.");
        }

        User user = userService.getUserById(userId);
        user.setSuspended(true);
        user.setSuspendedUntil(suspendUntil);
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/home/admin/restoreUser")
    public ResponseEntity<?> restoreUser(@RequestBody Map<String, Long> request) throws IOException {
        Long userId = request.get("userId");
        User user = userService.getUserById(userId);
        user.setSuspended(false);
        user.setSuspendedUntil(null);
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/home/admin/contactMessages/delete/{id}")
    public String deleteMessage(@PathVariable("id") Long id) {
    	ContactMessage message = contactMessageService.getContactMessagesById(id);
        contactMessageService.deleteContactMessage(message);
        if(message.getReason() == ContactReason.GAME_RECOMMENDATION) {
        	return "redirect:/home/admin/contactMessages/GAME_RECOMMENDATION";
        }else {
        	return "redirect:/home/admin/contactMessages/BUG";
        }
        
    }
    
    @PostMapping("/createPublication")
    public String createPost(@RequestParam("textContent") String textContent,
    		@Nullable @RequestParam("images") MultipartFile[] images,
    		@Nullable @RequestParam("gameId") Long gameId) throws IOException {
        setCurrentUser();
        Publication publication = new Publication();
        
        if (textContent.length() > 150) {
            return "redirect:/home";
        }
        publication.setTextContent(textContent);
        publication.setUser(currentUser);
        publication.setCreatedAt(new Date());
        
        if (gameId != null) {
            Game game = gameService.getGameById(gameId);
            publication.setGame(game);
        }

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
