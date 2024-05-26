package com.gamegaze.service.impl;
import com.gamegaze.domain.Follow;
import com.gamegaze.domain.FollowStatus;
import com.gamegaze.domain.User;
import com.gamegaze.repository.FollowRepository;
import com.gamegaze.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Override
    public List<Follow> getAllFollowedByUser(User user) {
        return followRepository.findByFollower(user);
    }

    @Override
    public void followUser(User currentUser, User userToFollow) {
        Follow follow = new Follow();
        follow.setFollower(currentUser);
        follow.setFollowed(userToFollow);
        follow.setStatus(FollowStatus.PENDING);
        followRepository.save(follow);
    }

    @Override
    public void unfollowUser(User currentUser, User userToUnfollow) {
        Follow follow = followRepository.findByFollowerAndFollowed(currentUser, userToUnfollow);
        if (follow != null) {
            followRepository.delete(follow);
        }
    }

    @Override
    public boolean isFollowing(User currentUser, User userToCheck) {
        Follow follow = followRepository.findByFollowerAndFollowed(currentUser, userToCheck);
        return follow != null;
    }
}
