package com.sprinthub.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.sprinthub.entity.Task;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendTaskStatusUpdate")
    @SendTo("/topic/taskStatusUpdates")
    public Task sendTaskStatusUpdate(Task task) {

        messagingTemplate.convertAndSend("/topic/taskStatusUpdates", task);

        return task;
    }
    
    
    
}


