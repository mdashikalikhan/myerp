package com.startup.myerp.controller;

import com.startup.myerp.service.MessagePublisher;
import com.startup.myerp.service.SseEmitterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/app")
@AllArgsConstructor
public class ChatController {
    private final MessagePublisher messagePublisher;
    private final SseEmitterService sseEmitterService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody Map<String, String> request){
        String sender = SecurityContextHolder.getContext().getAuthentication().getName();
        String content = request.get("message");

        Map<String, String> message = new HashMap<>();
        message.put("sender", sender);
        message.put("content", content);
        messagePublisher.publish(message);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/typing")
    public ResponseEntity<Void> handleTyping(){
        String sender = SecurityContextHolder.getContext().getAuthentication().getName();
        sseEmitterService.sendTypingEvent(sender + " is Typing...");
        return ResponseEntity.ok().build();
    }
}
