package com.gamegaze.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gamegaze.domain.Game;
import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;
import com.gamegaze.service.GameService;
import com.gamegaze.service.PublicationService;
import com.gamegaze.service.UserService;

@Controller
@RequestMapping("/games")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private PublicationService publicationService;
	
	@Autowired
	private UserService userService;
	
	private User currentUser;
	
    @GetMapping
    public String games(Model model) {
		setCurrentUser();
		List<Game> games = gameService.getAllGames();
		model.addAttribute("games",games);
		model.addAttribute("currentuser",currentUser);
		
		return "games";
    }
    
    @GetMapping("/{gameTitle}/publications")
    public ModelAndView gamesPublications(@PathVariable String gameTitle) {
		setCurrentUser();
		ModelAndView modelandview = new ModelAndView("gamePublications");
		modelandview.addObject("currentuser",currentUser);
		
		Game game = gameService.getGameByTitle(gameTitle);
		List<Publication> publications = publicationService.getPublicationsByGameId(game.getId());
		modelandview.addObject("game",game);
		modelandview.addObject("publications",publications);
		return modelandview;
    }
    
    private void setCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        currentUser = (User) userService.loadUserByUsername(username);
    }

}
