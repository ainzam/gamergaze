package com.gamegaze.service.impl;
import com.gamegaze.domain.User;
import com.gamegaze.domain.UserRole;
import com.gamegaze.service.EmailService;
import com.gamegaze.service.RegistrationService;
import com.gamegaze.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailService emailService;

    @Override
    public String register(User user) {
        user.setRole(UserRole.ROLE_USER);
        String result = userService.signUpUser(user);

        String subject = "Welcome to GameGaze!";
        String text = "Dear " + user.getUsername() + ",\n\nThank you for registering at GameGaze. We're excited to have you!";

        try {
            emailService.sendEmail(user.getEmail(), subject, text);
        } catch (Exception e) {
            userService.deleteUser(user);
            throw new IllegalStateException("Failed to send email");
        }

        return result;
    }
}