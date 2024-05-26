package com.gamegaze.service;

import java.util.List;

import com.gamegaze.domain.Message;

public interface ChatService {
    void saveMessage(Message message);
    List<Message> getMessages(Long senderId, Long recipientId);
    Message getLastMessageBetweenUsers(Long userId1, Long userId2);
}