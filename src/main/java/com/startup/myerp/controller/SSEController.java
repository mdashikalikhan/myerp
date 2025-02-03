package com.startup.myerp.controller;

import com.startup.myerp.service.SseEmitterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
@AllArgsConstructor
public class SSEController {
    private final SseEmitterService sseEmitterService;

    @GetMapping("/messages")
    public SseEmitter streamMessages(){
        return sseEmitterService.createSseEmitter();
    }
}
