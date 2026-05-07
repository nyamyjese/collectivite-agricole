package com.example.collectivite.controller;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.MembershipFeeRequest;
import com.example.collectivite.dto.MembershipFeeResponse;
import com.example.collectivite.repository.CollectivityRepository;
import com.example.collectivite.repository.MembershipFeeRepository;
import com.example.collectivite.service.MembershipFeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MembershipFeeController {

    private final MembershipFeeService feeService;

    public MembershipFeeController() {
        DBConnection db = DBConnection.getInstance();
        MembershipFeeRepository feeRepo = new MembershipFeeRepository(db);
        CollectivityRepository colRepo = new CollectivityRepository(db);
        this.feeService = new MembershipFeeService(feeRepo, colRepo);
    }

    @PostMapping("/collectivities/{collectivityId}/membershipFees")
    public ResponseEntity<MembershipFeeResponse> createFee(@PathVariable String collectivityId,
                                                           @RequestBody MembershipFeeRequest request) {
        MembershipFeeResponse resp = feeService.createFee(collectivityId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/collectivities/{collectivityId}/membershipFees")
    public ResponseEntity<List<MembershipFeeResponse>> listFees(@PathVariable String collectivityId) {
        List<MembershipFeeResponse> fees = feeService.getFees(collectivityId);
        return ResponseEntity.ok(fees);
    }
}