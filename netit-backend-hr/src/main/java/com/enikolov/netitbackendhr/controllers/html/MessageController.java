package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.components.InfoMessage;
import com.enikolov.netitbackendhr.enums.MessageStyle;
import com.enikolov.netitbackendhr.models.general.Message;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.services.data.MessageDataService;
import com.enikolov.netitbackendhr.services.data.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private MessageDataService messageDataService;

    @GetMapping("/messages/show-all")
    public String getAllMessagesPage(Model model){
        User user               = this.userDataService.getLoggedUser();
        List<Message> messages  = this.messageDataService.getMyMessages();
        InfoMessage infoMessage = new InfoMessage();

        if(messages.isEmpty()){
            infoMessage.setMessage("There are no messages!");
            infoMessage.setStyle(MessageStyle.INFO_MSG);
        }

        model.addAttribute("user"           , user);
        model.addAttribute("infoMessage"    , infoMessage);
        model.addAttribute("messages"       , messages);
        return "messages/show-all";
    }
}
