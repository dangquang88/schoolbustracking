package com.school.schoolbustracking.Models;

public class ChatModels {
    private String message;
    private  String receiver;
    private  String sender;
    public  ChatModels(){

    }

    public ChatModels(String message, String receiver, String sender) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }
}
