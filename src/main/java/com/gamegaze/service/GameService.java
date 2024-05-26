package com.gamegaze.service;

import java.util.List;

import com.gamegaze.domain.Game;

public interface GameService {
    List<Game> getAllGames();
    Game getGameById(Long id);
    Game getGameByTitle(String title);
    void addGamesIfNotPresent(List<Game> games);
}