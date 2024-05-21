package com.gamegaze.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamegaze.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findBySenderIdAndRecipientIdOrSenderIdAndRecipientIdOrderByTimestamp(Long senderId1, Long recipientId1, Long senderId2, Long recipientId2);
    List<Message> findBySenderIdOrRecipientId(Long senderId, Long recipientId);
    Optional<Message> findTopBySenderIdAndRecipientIdOrSenderIdAndRecipientIdOrderByTimestampDesc(Long senderId, Long recipientId, Long senderId2, Long recipientId2);
}
