package com.startup.myerp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.security.PublicKey;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseEmitterService {
    private final Map<String, SseEmitter> emitters
            = new ConcurrentHashMap<String, SseEmitter>();

    public SseEmitter createSseEmitter() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        String key = UUID.randomUUID().toString();
        emitters.put(key, emitter);
        emitter.onTimeout(
                ()->emitters.remove(key)
        );
        emitter.onCompletion(()->emitters.remove(key));
        return emitter;
    }

    public void sendMessage(String message) {
        System.out.println(message);
        emitters.forEach((key, emitter) -> {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(key);
            }
        });
    }

    public void sendTypingEvent(String typingMessage){
        emitters.forEach((key, emitter) -> {
            try {
                emitter.send(SseEmitter.event().name("typing").data(typingMessage));
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(key);
            }
        });
    }
}
