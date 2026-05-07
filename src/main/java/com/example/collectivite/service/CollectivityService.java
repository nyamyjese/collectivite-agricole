package com.example.collectivite.service;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.*;
import com.example.collectivite.entity.*;
import com.example.collectivite.exception.*;
import com.example.collectivite.repository.*;
import com.example.collectivite.validator.CollectivityCreationValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CollectivityService {

    private final CollectivityRepository collectivityRepository;
    private final MembershipRepository membershipRepository;
    private final MemberRepository memberRepository;
    private final DBConnection dbConnection;
    private final CollectivityCreationValidator validator;

    public CollectivityService(CollectivityRepository collectivityRepository,
                               MembershipRepository membershipRepository,
                               MemberRepository memberRepository,
                               DBConnection dbConnection,
                               CollectivityCreationValidator validator) {
        this.collectivityRepository = collectivityRepository;
        this.membershipRepository = membershipRepository;
        this.memberRepository = memberRepository;
        this.dbConnection = dbConnection;
        this.validator = validator;
    }

    public Collectivity createCollectivity(CreateCollectivityRequest request) {
        List<String> errors = validator.validate(request);
        if (!errors.isEmpty()) {
            throw new BadRequestException(String.join("; ", errors));
        }
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);
            Collectivity collectivity = new Collectivity();
            collectivity.setUniqueNumber(request.getUniqueNumber());
            collectivity.setUniqueName(request.getUniqueName());
            collectivity.setSpecialty(request.getSpecialty());
            collectivity.setCreationDate(LocalDate.now());
            collectivity.setCity(request.getCity());
            collectivity.setAnnualContribution(request.getAnnualContribution());
            collectivity.setAuthorizationDate(LocalDate.now());
            collectivityRepository.save(collectivity);

            for (MemberRequest mr : request.getInitialMembers()) {
                Membership membership = new Membership();
                membership.setMemberId(mr.getMemberId());
                membership.setCollectivityId(collectivity.getId());
                membership.setPoste(mr.getPoste());
                membership.setStartDate(LocalDate.now());
                membership.setEndDate(null);
                membershipRepository.save(membership);
            }
            conn.commit();
            return collectivity;
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            throw new RuntimeException("Transaction error while creating collectivity", e);
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public CollectivityResponse getCollectivityById(String id) {
        Collectivity c = collectivityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collectivity not found with id " + id));
        List<Membership> memberships = membershipRepository.findActiveByCollectivity(id);
        List<MemberResponse> memberResponseList = new ArrayList<>();
        for (Membership m : memberships) {
            memberRepository.findById(m.getMemberId()).ifPresent(member -> {
                MemberResponse mr = new MemberResponse();
                mr.setId(member.getId());
                mr.setName(member.getName());
                mr.setLastName(member.getFirstName());
                mr.setBirthDate(member.getBirthDate());
                mr.setAdress(member.getAdress());
                mr.setProfession(member.getProfession());
                mr.setPhone(member.getPhone());
                mr.setEmail(member.getEmail());
                mr.setJoinDate(member.getJoinDate());
                mr.setStatus(member.getStatus());
                mr.setCollectiviteId(id);
                mr.setPoste(m.getPoste());
                memberResponseList.add(mr);
            });
        }
        CollectivityResponse resp = new CollectivityResponse();
        resp.setId(c.getId());
        resp.setUniqueNumber(c.getUniqueNumber());
        resp.setUniqueName(c.getUniqueName());
        resp.setSpecialty(c.getSpecialty());
        resp.setCreationDate(c.getCreationDate());
        resp.setCity(c.getCity());
        resp.setAnnualContribution(c.getAnnualContribution());
        resp.setAuthorizationDate(c.getAuthorizationDate());
        resp.setMembers(memberResponseList);
        return resp;
    }

    public CollectivityResponse updateCollectivityInformation(String id, UpdateCollectivityInformationRequest request) {
        Collectivity c = collectivityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collectivity not found"));
        if (c.getUniqueNumber() != null && !c.getUniqueNumber().isEmpty()) {
            throw new BadRequestException("Unique number already assigned and cannot be changed");
        }
        if (c.getUniqueName() != null && !c.getUniqueName().isEmpty()) {
            throw new BadRequestException("Unique name already assigned and cannot be changed");
        }
        if (collectivityRepository.existsByUniqueNumber(request.getUniqueNumber())) {
            throw new BadRequestException("Unique number already used by another collectivity");
        }
        if (collectivityRepository.existsByUniqueName(request.getUniqueName())) {
            throw new BadRequestException("Unique name already used by another collectivity");
        }
        c.setUniqueNumber(request.getUniqueNumber());
        c.setUniqueName(request.getUniqueName());
        collectivityRepository.save(c);
        return getCollectivityById(id);
    }
}