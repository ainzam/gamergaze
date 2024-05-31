package com.gamegaze.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;
import com.gamegaze.domain.UserLike;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
	
	List<UserLike> findByUser(User user);
	List<UserLike> findByPublication(Publication publication);
	boolean existsByUserAndPublication(User user, Publication publication);
	Optional<UserLike> findByUserAndPublication(User user, Publication publication);
    
}