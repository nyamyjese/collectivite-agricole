package com.example.collectivite.controller;

import com.example.collectivite.dto.*;
import com.example.collectivite.service.ContributionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ContributionController {

    private final ContributionService contributionService;

    public ContributionController(ContributionService contributionService) {
        this.contributionService = contributionService;
    }

    /**
     * POST /collectivities/{collectivityId}/contributions
     * Enregistre une nouvelle cotisation.
     */
    @PostMapping("/collectivities/{collectivityId}/contributions")
    public ResponseEntity<ContributionResponse> recordContribution(
            @PathVariable String collectivityId,
            @RequestBody CreateContributionRequest request) {
        ContributionResponse response = contributionService.recordContribution(collectivityId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /collectivities/{collectivityId}/contributions
     * Liste les cotisations d'une collectivité avec filtres optionnels et pagination.
     */
    @GetMapping("/collectivities/{collectivityId}/contributions")
    public ResponseEntity<PageResponse<ContributionResponse>> listContributions(
            @PathVariable String collectivityId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String memberId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit) {

        List<ContributionResponse> items = contributionService.listContributions(
                collectivityId, type, memberId, startDate, endDate, page, limit);
        int total = contributionService.countContributions(collectivityId, type, memberId, startDate, endDate);
        PageResponse<ContributionResponse> response = new PageResponse<>(items, page, limit, total);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /collectivities/{collectivityId}/contributions/{contributionId}
     * Récupère une cotisation spécifique.
     */
    @GetMapping("/collectivities/{collectivityId}/contributions/{contributionId}")
    public ResponseEntity<ContributionResponse> getContribution(
            @PathVariable String collectivityId,
            @PathVariable String contributionId) {
        ContributionResponse response = contributionService.getContribution(collectivityId, contributionId);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /collectivities/{collectivityId}/contributions/reversements
     * Calcule les reversements à la fédération sur une période.
     */
    @GetMapping("/collectivities/{collectivityId}/contributions/reversements")
    public ResponseEntity<ReversementResponse> getReversements(
            @PathVariable String collectivityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ReversementResponse response = contributionService.getReversements(collectivityId, startDate, endDate);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /members/{memberId}/contributions
     * Liste les cotisations d'un membre spécifique.
     */
    @GetMapping("/members/{memberId}/contributions")
    public ResponseEntity<PageResponse<ContributionResponse>> listMemberContributions(
            @PathVariable String memberId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit) {

        List<ContributionResponse> items = contributionService.listMemberContributions(memberId, startDate, endDate, page, limit);
        int total = contributionService.countMemberContributions(memberId, startDate, endDate);
        PageResponse<ContributionResponse> response = new PageResponse<>(items, page, limit, total);
        return ResponseEntity.ok(response);
    }
}