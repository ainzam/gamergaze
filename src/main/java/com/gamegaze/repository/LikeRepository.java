package com.gamegaze.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamegaze.domain.Like;
import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;

public interface LikeRepository extends JpaRepository<Like, Long> {
	
	List<Like> findByUser(User user);
	List<Like> findByPublication(Publication publication);
	boolean existsByUserAndPublication(User user, Publication publication);
    
}