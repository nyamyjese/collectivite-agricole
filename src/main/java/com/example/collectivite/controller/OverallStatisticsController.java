package com.example.collectivite.controller;

import com.example.collectivite.dto.OverallStatisticsItemDto;
import com.example.collectivite.service.OverallStatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
public class OverallStatisticsController {

    private final OverallStatisticsService service;

    public OverallStatisticsController(OverallStatisticsService service) {
        this.service = service;
    }

    @GetMapping("/collectivites/statistics")
    public ResponseEntity<List<OverallStatisticsItemDto>> getOverallStatistics(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        List<OverallStatisticsItemDto> stats = service.getOverallStatistics(from, to);
        return ResponseEntity.ok(stats);
    }
}