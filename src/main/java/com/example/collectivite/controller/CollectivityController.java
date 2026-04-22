package com.example.collectivite.controller;

import com.example.collectivite.dto.CreateCollectivityRequest;
import com.example.collectivite.entity.Collectivity;
import com.example.collectivite.exception.BadReqestException;
import com.example.collectivite.service.CollectivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    private final CollectivityService collectivityService;

    public CollectivityController(CollectivityService collectivityService) {
        this.collectivityService = collectivityService;
    }

    @PostMapping
    public ResponseEntity<?> createCollectivity(@RequestBody CreateCollectivityRequest request) {
        try {
            Collectivity created = collectivityService.createCollectivity(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (BadReqestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}