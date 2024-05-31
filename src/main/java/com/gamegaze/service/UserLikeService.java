package com.gamegaze.service;

import java.util.List;

import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;
import com.gamegaze.domain.UserLike;

public interface UserLikeService {
	
	List<UserLike> getLikesByUser(User user);
	List<UserLike> getLikesByPublication(Publication publication);
	boolean existsByUserAndPublication(User user, Publication publication);
	UserLike getLikeByUserAndPublication(User user, Publication publication);
	void saveLike(UserLike like);
	void deleteLike(UserLike like);
}
