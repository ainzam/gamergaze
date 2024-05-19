package com.gamegaze.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;
import com.gamegaze.repository.PublicationRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublicationService {

    @Autowired
    private final PublicationRepository publicationRepository;
    
    public void savePublication(Publication publication) {
    	publicationRepository.save(publication);
    }
	
    public List<Publication> getPublicationsByUser(User user) {
        return publicationRepository.findByUserOrderByCreatedAt(user);
    }
    
}
