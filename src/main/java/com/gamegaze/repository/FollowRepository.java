package com.gamegaze.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamegaze.domain.Follow;
import com.gamegaze.domain.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    
    Follow findByFollowerAndFollowed(User follower, User followed);
    
    List<Follow> findByFollower(User follower);
    
    List<Follow> findByFollowed(User followed);
    
}