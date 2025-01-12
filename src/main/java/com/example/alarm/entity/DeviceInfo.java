package com.example.alarm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deviceInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceKey;

    @Column(unique = true)
    private String deviceId;

    @Column(unique = true)
    private String token;
}