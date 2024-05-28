package com.gamegaze.service;

import java.util.List;

import com.gamegaze.domain.Comment;
import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;

public interface PublicationService {
    void savePublication(Publication publication);
    List<Publication> getPublicationsByUser(User user);
    List<Publication> getPublicationsByGameId(Long gameId);
    List<Publication> getAllPublications();
    void deletePublication(Publication publication);
    void addCommentToPublication(Comment comment);
    List<Comment> getCommentsByPublication(Publication publication);
    Publication getPublicationById(Long publicationId);
    void updatePublication(Publication publication);
}
