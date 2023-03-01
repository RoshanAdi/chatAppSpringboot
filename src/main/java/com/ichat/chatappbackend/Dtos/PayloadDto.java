package com.ichat.chatappbackend.Dtos;


public class PayloadDto {
    private String user;
    private String message;
    private String senderId;
    private String recieverId;

    public PayloadDto(String user, String message, String senderId, String recieverId) {
        this.user = user;
        this.message = message;
        this.senderId = senderId;
        this.recieverId = recieverId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }
}
