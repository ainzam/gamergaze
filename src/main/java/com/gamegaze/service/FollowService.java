package com.gamegaze.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamegaze.domain.Follow;
import com.gamegaze.domain.FollowStatus;
import com.gamegaze.domain.User;
import com.gamegaze.repository.FollowRepository;

@Service
public class FollowService {
    
    @Autowired
    private FollowRepository followRepository;
    
    
    public List<Follow> getAllFollowedByUser(User user){
    	return followRepository.findByFollower(user);
    }
    
    public void followUser(User currentUser, User userToFollow) {
        Follow follow = new Follow();
        follow.setFollower(currentUser);
        follow.setFollowed(userToFollow);
        follow.setStatus(FollowStatus.PENDING);
        
        followRepository.save(follow);
    }
    
    public void unfollowUser(User currentUser, User userToUnfollow) {
        Follow follow = followRepository.findByFollowerAndFollowed(currentUser, userToUnfollow);
        if (follow!= null) {
            followRepository.delete(follow);
        }
    }
}
