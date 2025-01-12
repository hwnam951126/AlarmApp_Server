package com.example.alarm.controller;

import com.example.alarm.entity.DeviceInfo;
import com.example.alarm.service.DeviceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/device")
public class DeviceInfoController {
    private final Logger logger = LoggerFactory.getLogger(DeviceInfoController.class);
    private DeviceInfoService deviceInfoService;

    public DeviceInfoController(DeviceInfoService deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addDeviceInfo(@RequestBody DeviceInfo deviceInfo) {
        logger.info("addDeviceInfo add >>");
        try {
            deviceInfoService.addDeviceInfo(deviceInfo);
            return ResponseEntity.ok("DeviceInfo saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving DeviceInfo");
        }
    }
}
