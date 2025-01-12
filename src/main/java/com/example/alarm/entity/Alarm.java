package com.example.alarm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "alarms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceId;  // 디바이스 ID

    @ElementCollection
    @CollectionTable(name = "alarm_days_of_week", joinColumns = @JoinColumn(name = "alarm_id"))
    @Column(name = "day_of_week")
    private List<String> daysOfWeek;  // 반복 요일 (월, 화, 수 등)

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime scheduledTime;

    private LocalDate expirationDate;

    private boolean isEnabled;  // 알람 활성화 여부

    private boolean isRecurring;  // 알람 반복 여부

    private String alarmName;  // 알람 이름 (예: '기상 알람', '회의 알람')

    private String sound;  // 알람 사운드 파일 경로 또는 사운드 이름
}