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
    
    public Message getLastMessageBetweenUsers(Long userId1, Long userId2) {
        return messageRepository.findTopBySenderIdAndRecipientIdOrSenderIdAndRecipientIdOrderByTimestampDesc(
            userId1, userId2, userId2, userId1
        ).orElse(null);
    }
}