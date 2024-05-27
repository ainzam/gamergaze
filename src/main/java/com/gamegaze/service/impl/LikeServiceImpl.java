package com.gamegaze.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gamegaze.domain.Like;
import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;
import com.gamegaze.repository.LikeRepository;
import com.gamegaze.service.LikeService;

public class LikeServiceImpl implements LikeService{
	
	@Autowired
	private LikeRepository likeRepository;

	@Override
	public List<Like> getLikesByUser(User user) {
		return likeRepository.findByUser(user);
	}
	
	@Override
	public List<Like> getLikesByPublication(Publication publication) {
		return likeRepository.findByPublication(publication);
	}
	
	@Override
    public boolean existsByUserAndPublication(User user, Publication publication) {
        return likeRepository.existsByUserAndPublication(user, publication);
    }

	@Override
	public void saveLike(Like like) {
		likeRepository.save(like);
	}
	
}
