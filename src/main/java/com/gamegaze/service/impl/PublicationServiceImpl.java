package com.gamegaze.service.impl;
import com.gamegaze.domain.Comment;
import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;
import com.gamegaze.repository.CommentRepository;
import com.gamegaze.repository.PublicationRepository;
import com.gamegaze.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void savePublication(Publication publication) {
        publicationRepository.save(publication);
    }
    
    @Override
    public void updatePublication(Publication publication){
        
    	Publication existingPublication = publicationRepository.findById(publication.getId()).orElse(null);
    	existingPublication.setComments(publication.getComments());
    	existingPublication.setLikeCount(publication.getLikeCount());
    	
    	publicationRepository.save(publication);
    }


    @Override
    public List<Publication> getPublicationsByUser(User user) {
        return publicationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    @Override
    public List<Publication> getPublicationsByGameId(Long gameId) {
        return publicationRepository.findByGameIdOrderByCreatedAtDesc(gameId);
    }

    @Override
    public void addCommentToPublication(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPublication(Publication publication) {
        return commentRepository.findByPublicationOrderByCreatedAt(publication);
    }

    @Override
    public Publication getPublicationById(Long publicationId) {
        return publicationRepository.findById(publicationId).orElse(null);
    }

	@Override
	public List<Publication> getAllPublications() {
		return publicationRepository.findAll();
	}

	@Override
	public void deletePublication(Publication publication) {
		publicationRepository.delete(publication);
		
	}
}