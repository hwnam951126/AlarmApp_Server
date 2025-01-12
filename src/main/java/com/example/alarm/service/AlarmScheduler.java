package com.example.alarm.service;

import com.example.alarm.dto.FcmMessageDto;
import com.example.alarm.entity.Alarm;
import com.example.alarm.entity.DeviceInfo;
import com.example.alarm.repository.DeviceInfoRepository;
import com.google.firebase.messaging.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class AlarmScheduler {
    private final Logger logger = LoggerFactory.getLogger(AlarmScheduler.class);

    private final TaskScheduler taskScheduler;
    private final FcmService fcmService;
    private final DeviceInfoRepository deviceInfoRepository;

    // 생성자 주입을 통해 TaskScheduler와 FcmService, DeviceInfoRepository를 주입받음
    public AlarmScheduler(TaskScheduler taskScheduler, FcmService fcmService, DeviceInfoRepository deviceInfoRepository) {
        this.taskScheduler = taskScheduler;
        this.fcmService = fcmService;
        this.deviceInfoRepository = deviceInfoRepository;
    }

    // 알람을 스케줄링하는 메서드
    public void scheduleAlarm(Alarm alarm) {
        LocalDateTime scheduledTime = alarm.getScheduledTime();

        // scheduledTime을 Date로 변환하여 스케줄링
        Date triggerDate = Date.from(scheduledTime.atZone(ZoneId.systemDefault()).toInstant());

        // 스케줄 작업 등록
        taskScheduler.schedule(() -> triggerAlarm(alarm), triggerDate);
    }

    // 스케줄된 시간에 호출되는 메서드
    private void triggerAlarm(Alarm alarm) {
        logger.info("Triggering alarm: " + alarm);

        // DeviceInfo 확인
        DeviceInfo deviceInfoData = deviceInfoRepository.findByDeviceId(alarm.getDeviceId());
        if (deviceInfoData == null) {
            logger.error("No device info found for deviceId: " + alarm.getDeviceId());
            return;  // 디바이스 정보가 없으면 푸시 알림 전송 중단
        }

        // 푸시 알림 메시지 생성
        FcmMessageDto message = FcmMessageDto.builder()
                .title("알림 제목")
                .body("알림 내용")
                .token(deviceInfoData.getToken())
                .build();

        // 푸시 알림 전송
        sendPushNotification(message);
    }

    // FCM 푸시 알림 전송
    private void sendPushNotification(FcmMessageDto message) {
        try {
            fcmService.sendNotification(message);
            logger.info("푸시 알림 전송 성공: " + message);
        } catch (Exception e) {
            logger.error("푸시 알림 전송 실패: " + message, e);
        }
    }

    // 알람 취소 (스케줄 취소 기능 추가)
    public void cancelAlarm(Alarm alarm) {
        // 스케줄링된 작업을 취소하려면 추가적으로 관리가 필요
        logger.info("Canceling alarm: " + alarm.getId());
        // TODO: 스케줄 취소 로직 구현 (TaskScheduler에서는 Future 객체로 관리 필요)
    }
}
