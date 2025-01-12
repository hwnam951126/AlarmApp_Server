package com.example.alarm.service;

import com.example.alarm.entity.Alarm;
import com.example.alarm.repository.AlarmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlarmService {

    private static final Logger logger = LoggerFactory.getLogger(AlarmService.class);

    private final AlarmRepository alarmRepository;
    private final AlarmScheduler alarmScheduler;

    // 생성자 주입
    public AlarmService(AlarmRepository alarmRepository, AlarmScheduler alarmScheduler) {
        this.alarmRepository = alarmRepository;
        this.alarmScheduler = alarmScheduler;
    }

    public Alarm createAlarm(Alarm alarm) {
        // 알람을 저장하고 스케줄러에 등록
        Alarm savedAlarm = alarmRepository.save(alarm);
        alarmScheduler.scheduleAlarm(savedAlarm);

        return savedAlarm;
    }

    public Alarm updateAlarm(Alarm updatedAlarm) {
        Alarm alarm = alarmRepository.findById(updatedAlarm.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid alarm ID"));

        // 알람 정보 업데이트
        alarm.setScheduledTime(updatedAlarm.getScheduledTime());
        alarm.setDaysOfWeek(updatedAlarm.getDaysOfWeek());
        alarm.setEnabled(updatedAlarm.isEnabled());  // isEnabled() 사용

        // 스케줄러에 알람 갱신
        alarmScheduler.cancelAlarm(alarm);  // 기존 알람 취소
        alarmScheduler.scheduleAlarm(alarm);  // 새 알람 등록

        return alarmRepository.save(alarm);
    }

    public List<Alarm> getAlarmList(String deviceId) {
        //단말에 저장되어있는 알림 리스트 반환
        List<Alarm> alarmList  = alarmRepository.findByDeviceId(deviceId);

        return alarmList;
    }

    public Alarm enableAlarm(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid alarm ID"));
        alarm.setEnabled(true);
        alarmScheduler.scheduleAlarm(alarm);
        return alarmRepository.save(alarm);
    }

    public Alarm disableAlarm(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid alarm ID"));
        alarm.setEnabled(false);
        alarmScheduler.cancelAlarm(alarm);
        return alarmRepository.save(alarm);
    }

    public void deleteAlarm(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid alarm ID"));

        // 스케줄러에서 알람 삭제
        alarmScheduler.cancelAlarm(alarm);

        // 알람 삭제
        alarmRepository.delete(alarm);
    }

    public boolean isDuplicateAlarm(Alarm alarm) {
        return alarmRepository.existsByDeviceIdAndScheduledTimeAndDaysOfWeek(
                alarm.getDeviceId(), alarm.getScheduledTime(), alarm.getDaysOfWeek());
    }

    public void disableExpiredAlarms() {
        LocalDateTime now = LocalDateTime.now();
        List<Alarm> expiredAlarms = alarmRepository.findByIsEnabledTrueAndExpirationDateBefore(now);

        for (Alarm alarm : expiredAlarms) {
            alarm.setEnabled(false);
            alarmScheduler.cancelAlarm(alarm);
            alarmRepository.save(alarm);
            logger.info("만료된 알람 비활성화: {}", alarm.getId());
        }
    }
}
