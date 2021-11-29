package com.enikolov.netitbackendhr.models.DTO;

public class ChangeMailDTO {
    private int userId;
    private String newMail;

    public ChangeMailDTO(){

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNewMail() {
        return newMail;
    }

    public void setNewMail(String newMail) {
        this.newMail = newMail;
    }
}
