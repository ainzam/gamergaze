package com.gamegaze.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gamegaze.domain.Comment;
import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;
import com.gamegaze.domain.UserLike;
import com.gamegaze.service.UserLikeService;
import com.gamegaze.service.PublicationService;
import com.gamegaze.service.UserService;

@Controller
@RequestMapping("/publications")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;
 
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserLikeService userLikeService;
	
	private User currentUser;
    
    @PostMapping("/{publicationId}/comments")
    public String addComment(@PathVariable Long publicationId, @RequestParam String commentText) {
    	setCurrentUser();
    	Publication publication = publicationService.getPublicationById(publicationId);
        Comment comment = new Comment();
        comment.setUser(currentUser);
        comment.setTextContent(commentText);
        comment.setPublication(publication);
        comment.setCreatedAt(new Date());
        publicationService.addCommentToPublication(comment);
        
        return "redirect:/publications/" + publicationId + "/post";
    }
    
    @PostMapping("/{publicationId}/like")
    @ResponseBody
    public int likePublication(@PathVariable Long publicationId, @RequestParam Long userId) {
        Publication publication = publicationService.getPublicationById(publicationId);
        User user = userService.getUserById(userId);

        if (!userLikeService.existsByUserAndPublication(user, publication)) {
            UserLike like = new UserLike();
            like.setUser(user);
            like.setPublication(publication);
            userLikeService.saveLike(like);
        }
        publication.setLikeCount(userLikeService.getLikesByPublication(publication).size());
        publicationService.updatePublication(publication);
        return userLikeService.getLikesByPublication(publication).size();
    }

    @GetMapping("/{publicationId}/post")
    public ModelAndView getComments(@PathVariable Long publicationId) {
    	ModelAndView modelandview = new ModelAndView("post");
    	setCurrentUser();
    	modelandview.addObject("currentuser",currentUser);
        Publication publication = publicationService.getPublicationById(publicationId);
        modelandview.addObject("publication",publication);
        return modelandview;
    }
    
    private void setCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        currentUser = (User) userService.loadUserByUsername(username);
    }
}