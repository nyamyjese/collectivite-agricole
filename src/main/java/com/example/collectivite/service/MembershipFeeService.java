package com.example.collectivite.service;

import com.example.collectivite.dto.MembershipFeeRequest;
import com.example.collectivite.dto.MembershipFeeResponse;
import com.example.collectivite.entity.MembershipFee;
import com.example.collectivite.exception.BadRequestException;
import com.example.collectivite.repository.CollectivityRepository;
import com.example.collectivite.repository.MembershipFeeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class MembershipFeeService {

    private final MembershipFeeRepository feeRepository;
    private final CollectivityRepository collectivityRepository;

    public MembershipFeeService(MembershipFeeRepository feeRepository, CollectivityRepository collectivityRepository) {
        this.feeRepository = feeRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public MembershipFeeResponse createFee(Integer collectivityId, MembershipFeeRequest request) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new BadRequestException("Collectivity not found");
        }
        if (!List.of("MENSUELLE", "ANNUELLE", "PONCTUELLE").contains(request.getType())) {
            throw new BadRequestException("Invalid fee type. Must be MENSUELLE, ANNUELLE or PONCTUELLE");
        }
        if (request.getAmount() == null || request.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must be positive");
        }

        MembershipFee fee = new MembershipFee();
        fee.setCollectivityId(collectivityId);
        fee.setType(request.getType());
        fee.setAmount(request.getAmount());
        fee.setDescription(request.getDescription());

        fee = feeRepository.save(fee);

        MembershipFeeResponse resp = new MembershipFeeResponse();
        resp.setId(fee.getId());
        resp.setCollectivityId(fee.getCollectivityId());
        resp.setType(fee.getType());
        resp.setAmount(fee.getAmount());
        resp.setDescription(fee.getDescription());
        resp.setMessage("Membership fee created successfully");
        return resp;
    }

    public List<MembershipFeeResponse> getFees(Integer collectivityId) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new BadRequestException("Collectivity not found");
        }
        List<MembershipFee> fees = feeRepository.findByCollectivityId(collectivityId);
        return fees.stream().map(f -> {
            MembershipFeeResponse resp = new MembershipFeeResponse();
            resp.setId(f.getId());
            resp.setCollectivityId(f.getCollectivityId());
            resp.setType(f.getType());
            resp.setAmount(f.getAmount());
            resp.setDescription(f.getDescription());
            return resp;
        }).collect(Collectors.toList());
    }
}
