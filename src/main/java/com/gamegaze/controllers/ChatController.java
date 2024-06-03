package com.gamegaze.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gamegaze.domain.Message;
import com.gamegaze.domain.User;
import com.gamegaze.service.ChatService;
import com.gamegaze.service.UserService;

@Controller
public class ChatController {

		@Autowired
		private UserService userService;
	 	
	 	@Autowired
	    private ChatService chatService;

	    @Autowired
	    private SimpMessagingTemplate messagingTemplate;

		private User currentUser;
	    
		@GetMapping("/chat/{recipientId}")
		public String chat(Model model, @PathVariable Long recipientId) {
			model.addAttribute("recipientId", recipientId);
			setCurrentUser();
			model.addAttribute("currentuser",currentUser);
			model.addAttribute("recipientUsername", userService.getUserById(recipientId).getUsername());
			model.addAttribute("messages", chatService.getMessages(recipientId, currentUser.getId()));
			model.addAttribute("currentUserId", currentUser.getId());
			return "chat";
		}
		
		@GetMapping("/chats")
		public String chats(Model model) {
			setCurrentUser();
			List<User> users = userService.getFollowedUsersByUser(currentUser);
			Map<Long, String> userLastMessages = new HashMap<>();
		    for (User user : users) {
		        Message lastMessage = chatService.getLastMessageBetweenUsers(currentUser.getId(), user.getId());
		        if(lastMessage != null) {
		        	userLastMessages.put(user.getId(), lastMessage.getContent());
		        }else {
		        	userLastMessages.put(user.getId(), "No messages yet");
		        }
		    }
		    model.addAttribute("userLastMessages", userLastMessages);
		    model.addAttribute("users",users);
			model.addAttribute("currentuser",currentUser);
			return "chats";
		}
		
		@MessageMapping("/chat")
		public void sendMessage(@Payload Message message) {
			message.setTimestamp(new Date());
			chatService.saveMessage(message);
			messagingTemplate.convertAndSend("/topic/messages/" + message.getRecipientId(), message);
		}
		
	    private void setCurrentUser() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();
	        currentUser = (User) userService.loadUserByUsername(username);
	    }
}