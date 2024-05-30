package com.gamegaze.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gamegaze.domain.ContactMessage;
import com.gamegaze.repository.ContactMessageRepository;

@Service
public class ContactMessageService {
	
	@Autowired
	private ContactMessageRepository contactMessageRepository;
	
	public void saveContactMessage(ContactMessage contactMessage) {
		contactMessageRepository.save(contactMessage);
	}
}
