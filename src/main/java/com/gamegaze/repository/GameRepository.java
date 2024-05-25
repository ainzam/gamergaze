package com.gamegaze.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamegaze.domain.Game;

public interface GameRepository extends JpaRepository<Game, Long>{
	Optional<Game> findByTitle(String title);
}
