package com.enikolov.netitbackendhr.components;

import org.springframework.stereotype.Component;

@Component
public class InfoMessage {
    private String message;
    private String type;

    public InfoMessage(){

    }
    public InfoMessage(String type, String message){
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
