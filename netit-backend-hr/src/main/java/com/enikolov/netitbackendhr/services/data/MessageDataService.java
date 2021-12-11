package com.enikolov.netitbackendhr.services.data;

import com.enikolov.netitbackendhr.components.SystemClock;
import com.enikolov.netitbackendhr.models.general.Message;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.general.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MessageDataService {
    @Autowired
    private SystemClock systemClock;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMyInboxMessages(){
        User user = this.userDataService.getLoggedUser();
        return this.messageRepository.getMessagesByRecipientId(user.getId());
    }
    public List<Message> getMyOutboxMessages(){
        User user = this.userDataService.getLoggedUser();
        return this.messageRepository.getMessagesBySenderId(user.getId());
    }
    public boolean canIReply(int id){
        User user = this.userDataService.getLoggedUser();
        Optional<Message> messageModel = this.messageRepository.findById(id);
        if(messageModel.isPresent()){
            Message message = messageModel.get();
            User recipient = message.getRecipient();
            if(recipient.getId() == user.getId()){
                return true;
            }
            return false;
        }
        return false;
    }
    public Message getMessageForReply(int id){
        return this.messageRepository.getById(id);
    }
    public void sendMessage(Message message){
        User user = this.userDataService.getLoggedUser();
        message.setSender(user);
        message.setDate(this.systemClock.getSystemDate());
        message.setTime(this.systemClock.getSystemTime());
        message.setUnread(true);
        this.messageRepository.save(message);
    }
    public void deleteMessage(int id){
        User user = this.userDataService.getLoggedUser();
        Optional<Message> messageModel = this.messageRepository.findById(id);
        if(messageModel.isPresent()){
            Message messageToDelete = messageModel.get();
            boolean imSender    = messageToDelete.getSender().getId() == user.getId();
            boolean imRecipient = messageToDelete.getRecipient().getId() == user.getId();
            if(imSender || imRecipient){
                this.messageRepository.delete(messageToDelete);
            }
        }
    }
    public boolean isUnreadMessages(){
        List<Message> allMessages = this.getMyInboxMessages();

        for(Message message : allMessages){
            if(message.getUnread()) return true;
        }

        return false;
    }
    public void setUnreadFalse(Message message){
        message.setUnread(false);
        this.messageRepository.save(message);
    }
}
