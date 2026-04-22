package com.example.collectivite.controller;

import com.example.collectivite.dto.AdmitMemberRequest;
import com.example.collectivite.dto.MemberResponse;
import com.example.collectivite.exception.AdmissionException;
import com.example.collectivite.service.MemberAdmissionService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberAdmissionService admissionService;

    public MemberController(MemberAdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @PostMapping
    public ResponseEntity<?> admitMember(@RequestBody AdmitMemberRequest request) {
        try {
            if (request.getCollectiviteId() == null) {
                throw new BadRequestException("Collectivity id is required");
            }
            MemberResponse response = admissionService.admitMember(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AdmissionException e) {
            return ResponseEntity.badRequest().body(e.getErrors());
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}