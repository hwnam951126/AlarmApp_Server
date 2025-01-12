package com.example.alarm.service;

import com.example.alarm.dto.FcmMessageDto;
import com.example.alarm.entity.Alarm;
import com.example.alarm.entity.DeviceInfo;
import com.example.alarm.repository.DeviceInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class ExpiredAlarmScheduler {
    private final Logger logger = LoggerFactory.getLogger(ExpiredAlarmScheduler.class);

    private final AlarmService alarmService;

    public ExpiredAlarmScheduler(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @Scheduled(cron = "0 0 * * * *") // 매시간 정각에 실행
    public void processExpiredAlarms() {
        alarmService.disableExpiredAlarms();
    }
}
