package com.gamegaze.service;

import java.util.List;

import com.gamegaze.domain.ContactMessage;


public interface ContactMessageService {
	void saveContactMessage(ContactMessage contactMessage);
	List<ContactMessage> getAllContactMessages();
	void deleteContactMessage(ContactMessage contactMessage);
	ContactMessage getContactMessagesById(Long id);
}
