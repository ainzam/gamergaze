package com.gamegaze.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamegaze.domain.Game;

public interface GameRepository extends JpaRepository<Game, Long>{

}
