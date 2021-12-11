package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.components.InfoMessage;
import com.enikolov.netitbackendhr.components.SystemClock;
import com.enikolov.netitbackendhr.enums.MessageStyle;
import com.enikolov.netitbackendhr.models.general.Applies;
import com.enikolov.netitbackendhr.models.general.Message;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.services.data.AppliesDataService;
import com.enikolov.netitbackendhr.services.data.MessageDataService;
import com.enikolov.netitbackendhr.services.data.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class MessageController {
    @Autowired
    private SystemClock systemClock;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private MessageDataService messageDataService;
    @Autowired
    private AppliesDataService appliesDataService;

    @GetMapping("/messages/inbox/show-all")
    public String getInboxMessagesPage(Model model){
        User user               = this.userDataService.getLoggedUser();
        List<Message> messages  = this.messageDataService.getMyInboxMessages();
        InfoMessage infoMessage = (InfoMessage) model.asMap().get("infoMessage");
        if(infoMessage == null){
            infoMessage = new InfoMessage();
        }

        if(messages.isEmpty()){
            infoMessage.setMessage("There are no messages!");
            infoMessage.setStyle(MessageStyle.INFO_MSG);
        }else{
            for(Message message : messages){
                this.messageDataService.setUnreadFalse(message);
            }
        }


        model.addAttribute("user"           , user);
        model.addAttribute("infoMessage"    , infoMessage);
        model.addAttribute("messages"       , messages);
        model.addAttribute("hasUnread", this.messageDataService.isUnreadMessages());
        return "messages/inbox";
    }
    @GetMapping("/messages/outbox/show-all")
    public String getOutboxMessagesPage(Model model){
        User user               = this.userDataService.getLoggedUser();
        List<Message> messages  = this.messageDataService.getMyOutboxMessages();
        InfoMessage infoMessage = (InfoMessage) model.asMap().get("infoMessage");
        if(infoMessage == null){
            infoMessage = new InfoMessage();
        }

        if(messages.isEmpty()){
            infoMessage.setMessage("There are no messages!");
            infoMessage.setStyle(MessageStyle.INFO_MSG);
        }

        model.addAttribute("user"           , user);
        model.addAttribute("infoMessage"    , infoMessage);
        model.addAttribute("messages"       , messages);
        model.addAttribute("hasUnread", this.messageDataService.isUnreadMessages());
        return "messages/outbox";
    }
    @GetMapping("/messages/reply/{id}")
    public RedirectView replyMessage(@PathVariable int id, RedirectAttributes redirectAttributes){
        InfoMessage infoMessage = new InfoMessage();
        try {
            if(this.messageDataService.canIReply(id)){
                Message messageForReply = this.messageDataService.getMessageForReply(id);
                String messageTitle = "RE: " + messageForReply.getTitle();
                redirectAttributes.addFlashAttribute("repliedMessage", messageForReply);
                redirectAttributes.addFlashAttribute("messageTitle", messageTitle);
                return new RedirectView("/messages/reply");
            }
            infoMessage.setMessage("Message not found!");
            infoMessage.setStyle(MessageStyle.ERROR_MSG);
        }catch (Exception e){
            e.printStackTrace();
            infoMessage.setMessage("Something went wrong! Message not sent.");
            infoMessage.setStyle(MessageStyle.ERROR_MSG);
        }

        redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
        return new RedirectView("/messages/inbox/show-all");
    }
    @GetMapping("/messages/reply")
    public String getReplyMessagePage(Model model){
        User user               = this.userDataService.getLoggedUser();
        Message repliedMessage  = (Message) model.asMap().get("repliedMessage");

        Message messageReply    = new Message();
        messageReply.setRecipient(repliedMessage.getSender());
        messageReply.setTitle("RE: " + repliedMessage.getTitle());

        model.addAttribute("messageReply", messageReply);
        model.addAttribute("user", user);
        model.addAttribute("hasUnread", this.messageDataService.isUnreadMessages());
        return "/messages/new";
    }
    @PostMapping("/messages/reply")
    public RedirectView replyMessage(@ModelAttribute Message messageReply){
        try{
            this.messageDataService.sendMessage(messageReply);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new RedirectView("/messages/outbox/show-all");
    }
    @GetMapping("/messages/delete/{id}")
    public RedirectView deleteMessage(@PathVariable int id){
        this.messageDataService.deleteMessage(id);

        return new RedirectView("/messages/inbox/show-all");
    }
    @GetMapping("/message/send/apply/{id}")
    public String getSendMessageToApplyPage(@PathVariable int id, Model model){
        User user = this.userDataService.getLoggedUser();
        Optional<Applies> applyModel = this.appliesDataService.getApplyById(id);
        if(applyModel.isPresent()){
            Applies apply = applyModel.get();
            User applicant = apply.getEmployee().getUser();

            Message newMessage = new Message();
            newMessage.setSender(user);
            newMessage.setRecipient(applicant);
            newMessage.setTitle("ABOUT APPLY FOR: " + apply.getCampaign().getTitle());

            model.addAttribute("messageReply", newMessage);
            model.addAttribute("user", user);
            model.addAttribute("hasUnread", this.messageDataService.isUnreadMessages());
            return "/messages/new";

        }
        return "/user-dispatch";
    }
}
