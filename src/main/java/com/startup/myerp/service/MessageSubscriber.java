package com.startup.myerp.service;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class MessageSubscriber implements MessageListener {
    private final SseEmitterService sseEmitterService;



    @Override
    public void onMessage(Message message, byte[] pattern) {

            String channel = new String(message.getChannel());
            String body = new String(message.getBody());
            sseEmitterService.sendMessage(body);

    }
}
