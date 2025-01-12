package com.example.alarm.controller;

import com.example.alarm.dto.FcmMessageDto;
import com.example.alarm.service.FcmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private final FcmService fcmService;

    public NotificationController(FcmService fcmService) {
        this.fcmService = fcmService;
    }

    @PostMapping("/send")
    public String sendNotification(@RequestBody FcmMessageDto fcmMessageDto) {
        fcmService.sendNotification(fcmMessageDto);
        return "Notification sent successfully!";
    }
}