package com.example.alarm.service;

import com.example.alarm.dto.FcmMessageDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;

    public FcmService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public void sendNotification(FcmMessageDto fcmMessageDto) {
        System.out.println("sendNotification >>");
        try {
            // Notification.Builder를 사용하여 Notification 객체 생성
            Notification notification = Notification.builder()
                    .setTitle(fcmMessageDto.getTitle())
                    .setBody(fcmMessageDto.getBody())
                    .build();

            Message message = Message.builder()
                    .setToken(fcmMessageDto.getToken())
                    .setNotification(notification)
                    .build();

            String response = firebaseMessaging.send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}