package com.gamegaze.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;
import com.gamegaze.repository.PublicationRepository;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;


    public List<Publication> findPublicationsByFollowedUsers(User user) {
        return publicationRepository.findPublicationsByFollowedUsers(user);
    }

}