package com.gamegaze.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.gamegaze.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("tu_correo@gmail.com"); // Cambia esto al correo que estás usando para enviar
        mailSender.send(message);
    }

}
