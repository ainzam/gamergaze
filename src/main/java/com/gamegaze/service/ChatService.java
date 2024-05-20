package com.gamegaze.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamegaze.domain.Message;
import com.gamegaze.repository.MessageRepository;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public List<Message> getMessages(Long senderId, Long recipientId) {
        return messageRepository.findBySenderIdAndRecipientIdOrSenderIdAndRecipientIdOrderByTimestamp(senderId, recipientId,recipientId,senderId);
    }
}