package com.gamegaze.service.impl;

import com.gamegaze.domain.Game;
import com.gamegaze.repository.GameRepository;
import com.gamegaze.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game getGameById(Long id) {
        return gameRepository.findById(id).orElse(null);
    }

    @Override
    public Game getGameByTitle(String title) {
        return gameRepository.findByTitle(title).orElse(null);
    }

    @Override
    public void addGamesIfNotPresent(List<Game> games) {
        List<Game> gamesDB = gameRepository.findAll();
        if (gamesDB.isEmpty()) {
            for (Game game : games) {
                gameRepository.save(game);
            }
        }
    }
}