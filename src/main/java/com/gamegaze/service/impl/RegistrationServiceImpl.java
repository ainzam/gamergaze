package com.gamegaze.service.impl;
import com.gamegaze.domain.User;
import com.gamegaze.domain.UserRole;
import com.gamegaze.service.RegistrationService;
import com.gamegaze.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserService userService;

    @Override
    public String register(User user) {
        user.setRole(UserRole.ROLE_USER);
        return userService.signUpUser(user);
    }
}