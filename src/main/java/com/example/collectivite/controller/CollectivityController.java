package com.example.collectivite.controller;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.*;
import com.example.collectivite.entity.Collectivity;
import com.example.collectivite.repository.*;
import com.example.collectivite.service.CollectivityService;
import com.example.collectivite.validator.CollectivityCreationValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    private final CollectivityService collectivityService;

    public CollectivityController() {
        DBConnection db = DBConnection.getInstance();
        CollectivityRepository collectivityRepo = new CollectivityRepository(db);
        MembershipRepository membershipRepo = new MembershipRepository(db);
        MemberRepository memberRepo = new MemberRepository(db);
        CollectivityCreationValidator validator = new CollectivityCreationValidator(memberRepo, membershipRepo, collectivityRepo);
        this.collectivityService = new CollectivityService(collectivityRepo, membershipRepo, memberRepo, db, validator);
    }

    @PostMapping
    public ResponseEntity<List<CollectivityResponse>> createCollectivities(@RequestBody List<CreateCollectivityRequest> requests) {
        List<CollectivityResponse> responses = new ArrayList<>();
        for (CreateCollectivityRequest req : requests) {
            Collectivity c = collectivityService.createCollectivity(req);
            CollectivityResponse resp = collectivityService.getCollectivityById(c.getId());
            responses.add(resp);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectivityResponse> getCollectivity(@PathVariable String id) {
        CollectivityResponse resp = collectivityService.getCollectivityById(id);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}/informations")
    public ResponseEntity<CollectivityResponse> updateInformations(@PathVariable String id,
                                                                   @RequestBody UpdateCollectivityInformationRequest request) {
        CollectivityResponse resp = collectivityService.updateCollectivityInformation(id, request);
        return ResponseEntity.ok(resp);
    }
}