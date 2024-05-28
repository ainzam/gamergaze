package com.gamegaze.service;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.gamegaze.domain.User;

public interface UserService extends UserDetailsService {
    String signUpUser(User user);
    void updateUser(User user) throws IOException;
    User getUserById(Long id);
    List<User> getFollowedUsersByUser(User currentUser);
    List<User> getAllUsers();
    void deleteUser(User user);
    User getUserByUsername(String username);
}
