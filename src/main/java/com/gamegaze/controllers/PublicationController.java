package com.gamegaze.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamegaze.domain.Comment;
import com.gamegaze.domain.Publication;
import com.gamegaze.service.PublicationService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/publications")
public class PublicationController {

    @Autowired
    private final PublicationService publicationService;
    
    @PostMapping("/{publicationId}/comments")
    public void addComment(@PathVariable Long publicationId, @RequestBody Comment comment) {
        Publication publication = publicationService.getPublicationById(publicationId);
        comment.setPublication(publication);
        comment.setCreatedAt(new Date());
        publicationService.addCommentToPublication(comment);
    }
    
    @GetMapping("/{publicationId}/like")
    @ResponseBody
    public int like(@PathVariable Long publicationId){
    	Publication publication = publicationService.getPublicationById(publicationId);
    	publication.setLikes(publication.getLikes() + 1);
    	publicationService.savePublication(publication);
    	return publication.getLikes();
    }

    @GetMapping("/{publicationId}/comments")
    public List<Comment> getComments(@PathVariable Long publicationId) {
        Publication publication = publicationService.getPublicationById(publicationId);
        return publicationService.getCommentsByPublication(publication);
    }
}