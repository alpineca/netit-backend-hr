package com.enikolov.netitbackendhr.services.data;

import com.enikolov.netitbackendhr.models.general.Message;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.general.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageDataService {
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMyMessages(){
        User user = this.userDataService.getLoggedUser();
        return this.messageRepository.getMessagesByRecipientId(user.getId());
    }
}
