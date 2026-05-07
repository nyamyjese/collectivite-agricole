package com.example.collectivite.controller;

import com.example.collectivite.dto.*;
import com.example.collectivite.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AttendanceController {
    private final AttendanceService service;
    public AttendanceController(AttendanceService service) { this.service = service; }

    @PostMapping("/collectivities/{id}/activities/{activityId}/attendance")
    public ResponseEntity<List<ActivityMemberAttendanceResponse>> create(
            @PathVariable String id,
            @PathVariable String activityId,
            @RequestBody List<CreateActivityMemberAttendanceRequest> reqs) {
        return ResponseEntity.status(201).body(service.recordAttendance(id, activityId, reqs));
    }

    @GetMapping("/collectivities/{id}/activities/{activityId}/attendance")
    public ResponseEntity<List<ActivityMemberAttendanceResponse>> get(
            @PathVariable String id,
            @PathVariable String activityId) {
        return ResponseEntity.ok(service.getAttendance(id, activityId));
    }
}