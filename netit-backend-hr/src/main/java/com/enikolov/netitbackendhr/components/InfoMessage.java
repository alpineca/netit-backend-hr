package com.enikolov.netitbackendhr.components;

import com.enikolov.netitbackendhr.enums.MessageStyle;
import org.springframework.stereotype.Component;

@Component
public class InfoMessage {
    private String message;
    private MessageStyle style;

    public InfoMessage(){

    }
    public InfoMessage(String message, MessageStyle style){
        this.style = style;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageStyle getStyle() {
        return style;
    }

    public void setStyle(MessageStyle style) {
        this.style = style;
    }
}
