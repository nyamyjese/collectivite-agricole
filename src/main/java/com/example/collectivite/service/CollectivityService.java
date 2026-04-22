package com.example.collectivite.service;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.CreateCollectivityRequest;
import com.example.collectivite.dto.MemberRequest;
import com.example.collectivite.entity.Collectivity;
import com.example.collectivite.entity.Membership;
import com.example.collectivite.exception.BadReqestException;
import com.example.collectivite.repository.CollectivityRepository;
import com.example.collectivite.repository.MembershipRepository;
import com.example.collectivite.validator.CollectivityCreationValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CollectivityService {

    private final CollectivityRepository collectivityRepository;
    private final MembershipRepository membershipRepository;
    private final DBConnection dbConnection;
    private final CollectivityCreationValidator validator;

    public CollectivityService(CollectivityRepository collectivityRepository,
                               MembershipRepository membershipRepository,
                               DBConnection dbConnection,
                               CollectivityCreationValidator validator) {
        this.collectivityRepository = collectivityRepository;
        this.membershipRepository = membershipRepository;
        this.dbConnection = dbConnection;
        this.validator = validator;
    }

    public Collectivity createCollectivity(CreateCollectivityRequest request) {
        List<String> errors = validator.validate(request);
        if (!errors.isEmpty()) {
            throw new BadReqestException(String.join("; ", errors));
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
            if (conn != null)
                try { conn.setAutoCommit(true);
                    conn.close();
                }
            catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
}