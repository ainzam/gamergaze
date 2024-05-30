package com.gamegaze.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gamegaze.domain.ContactMessage;
import com.gamegaze.domain.ContactReason;
import com.gamegaze.domain.User;
import com.gamegaze.service.ContactMessageService;
import com.gamegaze.service.UserService;

@Controller
@RequestMapping("/form")
public class ContactController {
	
	@Autowired
	private ContactMessageService contactMessageService;
	
	@Autowired
	private UserService userService;
	
	private User currentUser;
	
    @GetMapping
    public String getContactForm(Model model,@RequestParam(name = "success", required = false) String success) {
        setCurrentUser();
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("contactMessage", new ContactMessage());
        model.addAttribute("reasons", ContactReason.values());
        model.addAttribute("success", success);
        return "form";
    }

    @PostMapping
    public String submitContactForm(ContactMessage contactMessage) {
        setCurrentUser();
        contactMessage.setUser(currentUser);
        contactMessageService.saveContactMessage(contactMessage);
        return "redirect:/form?success";
    }
	
	private void setCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		currentUser = (User) userService.loadUserByUsername(username);
	}
}
