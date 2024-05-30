package com.gamegaze.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamegaze.domain.ContactMessage;
import com.gamegaze.repository.ContactMessageRepository;
import com.gamegaze.service.ContactMessageService;

@Service
public class ContactMessageServiceImpl implements ContactMessageService{
	
	@Autowired
	private ContactMessageRepository contactMessageRepository;

	
	@Override
	public List<ContactMessage> getAllContactMessages() {
		return contactMessageRepository.findAll();
	}
	
	@Override
	public void saveContactMessage(ContactMessage contactMessage) {
		contactMessageRepository.save(contactMessage);
	}

	@Override
	public void deleteContactMessage(ContactMessage contactMessage) {
		contactMessageRepository.delete(contactMessage);
	}

	@Override
	public ContactMessage getContactMessagesById(Long id) {
		return contactMessageRepository.findById(id).orElse(null);
	}

}