package com.example.alarm.controller;

import com.example.alarm.entity.Alarm;
import com.example.alarm.repository.AlarmRepository;
import com.example.alarm.service.AlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/alarms")
public class AlarmController {
    private final Logger logger = LoggerFactory.getLogger(AlarmController.class);
    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;

    public AlarmController(AlarmRepository alarmRepository, AlarmService alarmService) {
        this.alarmRepository = alarmRepository;
        this.alarmService = alarmService;
    }

    @PostMapping
    public Alarm createAlarm(@RequestBody Alarm alarm) {
        try {
            return alarmService.createAlarm(alarm);
        } catch (Exception e){
            logger.error("[createAlarm error]" + e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error");
        }
    }

    @PostMapping("/update")
    public Alarm updateAlarm(@RequestBody Alarm alarm) {
        try {
            return alarmService.updateAlarm(alarm);
        } catch (Exception e){
            logger.error("[updateAlarm error]" + e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error");
        }
    }

    @GetMapping
    public List<Alarm> getAlrmList(@RequestParam String deviceId) {
        logger.info("getAlrmList >> " + deviceId);
        try {
            return alarmService.getAlarmList(deviceId);
        } catch (Exception e){
            logger.error("[getAllAlarms error]" + e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteAlarm(@RequestParam Long id) {
        alarmService.deleteAlarm(id);
        return ResponseEntity.noContent().build(); // 204 No Content 반환
    }

    @PostMapping("/enable")
    public ResponseEntity<String> enableAlarm(@RequestParam Long id) {
        logger.info("enableAlarm >>");
        try {
            alarmService.enableAlarm(id);
            return ResponseEntity.ok("알람이 활성화되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 ID의 알람을 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알람 활성화 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/disable")
    public ResponseEntity<String> disableAlarm(@RequestParam Long id) {
        logger.info("disableAlarm >>");
        try {
            alarmService.disableAlarm(id);
            return ResponseEntity.ok("알람이 비활성화되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 ID의 알람을 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알람 비활성화 중 오류가 발생했습니다.");
        }
    }
}
