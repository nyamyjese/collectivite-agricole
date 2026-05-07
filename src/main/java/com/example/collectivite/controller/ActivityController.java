package com.example.collectivite.controller;

import com.example.collectivite.dto.*;
import com.example.collectivite.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ActivityController {
    private final ActivityService service;
    public ActivityController(ActivityService service) { this.service = service; }

    @PostMapping("/collectivities/{id}/activities")
    public ResponseEntity<List<CollectivityActivityResponse>> create(
            @PathVariable String id,
            @RequestBody List<CreateCollectivityActivityRequest> reqs) {
        return ResponseEntity.ok(service.createActivities(id, reqs));
    }

    @GetMapping("/collectivities/{id}/activities")
    public ResponseEntity<List<CollectivityActivityResponse>> list(@PathVariable String id) {
        return ResponseEntity.ok(service.getActivities(id));
    }
}