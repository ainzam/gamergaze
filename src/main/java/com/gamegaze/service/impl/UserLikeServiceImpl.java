package com.gamegaze.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;
import com.gamegaze.domain.UserLike;
import com.gamegaze.repository.UserLikeRepository;
import com.gamegaze.service.UserLikeService;

@Service
public class UserLikeServiceImpl implements UserLikeService{
	
	@Autowired
	private UserLikeRepository likeRepository;

	@Override
	public List<UserLike> getLikesByUser(User user) {
		return likeRepository.findByUser(user);
	}
	
	@Override
	public List<UserLike> getLikesByPublication(Publication publication) {
		return likeRepository.findByPublication(publication);
	}
	
	@Override
    public boolean existsByUserAndPublication(User user, Publication publication) {
        return likeRepository.existsByUserAndPublication(user, publication);
    }

	@Override
	public void saveLike(UserLike like) {
		likeRepository.save(like);
	}
	
}
