package com.startup.myerp.model;

import lombok.Data;

@Data
public class ChatMessage {

    public enum MessageType {
        CHAT, TYPING
    }

    private MessageType type;
    private String sender;
    private String content;
}
