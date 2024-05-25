package com.gamegaze.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamegaze.domain.Game;
import com.gamegaze.repository.GameRepository;

@Service
public class GameService {
	
	@Autowired
	private GameRepository gameRepository;

	public List<Game> getAllGames(){
		return gameRepository.findAll();
	}
	
	public Game getGameById(Long id) {
		return gameRepository.findById(id).orElse(null);
	}
	
	public Game getGameByTitle(String title) {
		return gameRepository.findByTitle(title).orElse(null);
	}
	
    public void addGamesIfNotPresent(List<Game> games) {
    	List<Game> gamesDB = gameRepository.findAll();
        if(gamesDB.isEmpty()) {
	    	for (Game game : games) {
	        	gameRepository.save(game);
	        }
        }
    }
	
}
