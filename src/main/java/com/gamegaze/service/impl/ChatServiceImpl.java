package com.gamegaze.service.impl;
import com.gamegaze.domain.Message;
import com.gamegaze.repository.MessageRepository;
import com.gamegaze.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages(Long senderId, Long recipientId) {
        return messageRepository.findBySenderIdAndRecipientIdOrSenderIdAndRecipientIdOrderByTimestamp(senderId, recipientId, recipientId, senderId);
    }

    @Override
    public Message getLastMessageBetweenUsers(Long userId1, Long userId2) {
        return messageRepository.findTopBySenderIdAndRecipientIdOrSenderIdAndRecipientIdOrderByTimestampDesc(userId1, userId2, userId2, userId1).orElse(null);
    }
}
