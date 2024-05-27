package com.gamegaze.service;

import java.util.List;

import com.gamegaze.domain.Like;
import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;

public interface LikeService {
	
	List<Like> getLikesByUser(User user);
	List<Like> getLikesByPublication(Publication publication);
	boolean existsByUserAndPublication(User user, Publication publication);
	void saveLike(Like like);
	

}
