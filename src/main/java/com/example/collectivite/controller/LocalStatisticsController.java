package com.example.collectivite.controller;

import com.example.collectivite.dto.LocalStatisticsItemDto;
import com.example.collectivite.service.LocalStatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
public class LocalStatisticsController {

    private final LocalStatisticsService service;

    public LocalStatisticsController(LocalStatisticsService service) {
        this.service = service;
    }

    @GetMapping("/collectivites/{id}/statistics")
    public ResponseEntity<List<LocalStatisticsItemDto>> getLocalStatistics(
            @PathVariable String id,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        List<LocalStatisticsItemDto> stats = service.getLocalStatistics(id, from, to);
        return ResponseEntity.ok(stats);
    }
}