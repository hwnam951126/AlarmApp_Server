package com.example.alarm.repository;

import com.example.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByDeviceId(String deviceId);

    boolean existsByDeviceIdAndScheduledTimeAndDaysOfWeek(String deviceId, LocalDateTime scheduledTime, List<String> daysOfWeek);

    // 만료일이 지난 활성화된 알람 조회
    List<Alarm> findByIsEnabledTrueAndExpirationDateBefore(LocalDateTime expirationDate);

}
