package com.gamegaze.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamegaze.domain.Publication;
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
	
}
