package com.gamegaze.service;

import java.util.List;

import com.gamegaze.domain.Follow;
import com.gamegaze.domain.User;

public interface FollowService {
    List<Follow> getAllFollowedByUser(User user);
    void followUser(User currentUser, User userToFollow);
    void unfollowUser(User currentUser, User userToUnfollow);
    boolean isFollowing(User currentUser, User userToCheck);
}
