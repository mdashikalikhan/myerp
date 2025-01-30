package com.startup.myerp.controller;

import com.startup.myerp.model.ChatMessage;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        return message;
    }

    @MessageMapping("/sendStatusUpdate")
    public void sendStatusUpdate(String status) {
        messagingTemplate.convertAndSend("/topic/employeeStatus", status);
    }

    @MessageMapping("/chat.typing")
    @SendTo("/topic/public")
    public ChatMessage typing(ChatMessage message) {
        return message; // Broadcast typing notification to all users
    }
}
