package com.gamegaze;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gamegaze.domain.Game;
import com.gamegaze.service.GameService;

@Configuration
public class GameDataInitializer {

    @Autowired
    private GameService gameService;

    @Bean
    public CommandLineRunner initializeGames() {
        return args -> {
            List<Game> defaultGames = Arrays.asList(
                new Game(null, "Tom Clancy's Rainbow 6 Siege", "imagen1.jpg", null),
                new Game(null, "Call of Duty: Modern Warfare 2", "imagen2.jpg", null),
                new Game(null, "League of Legends", "imagen3.jpg", null),
                new Game(null, "Valorant", "imagen4.jpg", null),
                new Game(null, "The Last Of Us Part: 1", "imagen5.jpg", null),
                new Game(null, "The Last Of Us Part: 2", "imagen6.jpg", null),
                new Game(null, "Hello Kitty Online", "imagen7.jpg", null),
                new Game(null, "Cyberpunk 2077", "imagen8.jpg", null),
                new Game(null, "Baldur's Gate 3", "imagen9.jpg", null)
            );

            gameService.addGamesIfNotPresent(defaultGames);
        };
    }
}
