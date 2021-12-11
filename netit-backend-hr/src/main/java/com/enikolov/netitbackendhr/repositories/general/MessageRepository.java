package com.enikolov.netitbackendhr.repositories.general;

import com.enikolov.netitbackendhr.models.general.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    public List<Message> getMessagesByRecipientId(int recipientId);
    public List<Message> getMessagesBySenderId(int senderId);
}
