package com.example.alarm.repository;

import com.example.alarm.entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, Long> {
    DeviceInfo findByDeviceId(String deviceId);
}
