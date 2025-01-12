package com.example.alarm.service;

import com.example.alarm.entity.DeviceInfo;
import com.example.alarm.repository.DeviceInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeviceInfoService {
    private final Logger logger = LoggerFactory.getLogger(DeviceInfoService.class);
    private DeviceInfoRepository deviceInfoRepository;

    public DeviceInfoService(DeviceInfoRepository deviceInfoRepository) {
        this.deviceInfoRepository = deviceInfoRepository;
    }

    public void addDeviceInfo(DeviceInfo deviceInfo) {
        logger.info("addDeviceInfo >>");
        DeviceInfo deviceInfoData = deviceInfoRepository.findByDeviceId(deviceInfo.getDeviceId());

        if (deviceInfoData == null) {
            deviceInfoRepository.save(deviceInfo);
        }

    }
}
