package com.example.collectivite.service;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.ContributionResponse;
import com.example.collectivite.dto.CreateContributionRequest;
import com.example.collectivite.dto.ReversementResponse;
import com.example.collectivite.entity.Contribution;
import com.example.collectivite.exception.BadRequestException;
import com.example.collectivite.repository.ContributionRepository;
import com.example.collectivite.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ContributionService {

    private final ContributionRepository contributionRepository;

    public ContributionService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }


     // Enregistre une cotisation.
    public ContributionResponse recordContribution(String collectivityId, CreateContributionRequest request) {
        // Validation du membre (hors transaction)
        validateMemberAndCollectivity(request.getMemberId(), collectivityId);
        validatePaymentMode(request.getPaymentMode(), request.getTransactionReference());

        if (request.getAmount() <= 0) {
            throw new BadRequestException("INVALID_AMOUNT", "Amount must be positive");
        }

        String id = "CONT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        int reversedAmount = 0;
        if ("PERIODIQUE_MENSUELLE".equals(request.getType()) || "PERIODIQUE_ANNUELLE".equals(request.getType())) {
            reversedAmount = (int) (request.getAmount() * 0.10);
        }

        Contribution contribution = new Contribution();
        contribution.setId(id);
        contribution.setMemberId(request.getMemberId());
        contribution.setCollectivityId(collectivityId);
        contribution.setType(request.getType());
        contribution.setAmount(request.getAmount());
        contribution.setPaymentMode(request.getPaymentMode());
        contribution.setTransactionReference(request.getTransactionReference());
        contribution.setCollectionDate(request.getCollectionDate());
        contribution.setFederationReversedAmount(reversedAmount);
        contribution.setStatus("ENREGISTRE");

        // Gestion de la transaction : une seule connexion pour le repository
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            contributionRepository.save(conn, contribution);
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de la contribution", e);
        }

        return ContributionResponse.fromEntity(contribution);
    }

    public List<ContributionResponse> listContributions(String collectivityId, String type, String memberId,
                                                        LocalDate startDate, LocalDate endDate,
                                                        int page, int limit) {
        try (Connection conn = DBConnection.getConnection()) {
            int offset = page * limit;
            List<Contribution> contributions = contributionRepository.findByCollectivityId(
                    conn, collectivityId, type, memberId, startDate, endDate, offset, limit);
            List<ContributionResponse> responses = new ArrayList<>();
            for (Contribution c : contributions) {
                responses.add(ContributionResponse.fromEntity(c));
            }
            return responses;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la liste des contributions", e);
        }
    }

    public ReversementResponse getReversements(String collectivityId, LocalDate startDate, LocalDate endDate) {
        try (Connection conn = DBConnection.getConnection()) {
            List<Map<String, Object>> rows = contributionRepository.getReversementDetails(conn, collectivityId, startDate, endDate);

            int totalPeriodic = 0;
            int totalReversed = 0;
            List<ReversementResponse.ReversementDetail> details = new ArrayList<>();

            for (Map<String, Object> row : rows) {
                int amount = ((Number) row.get("amount")).intValue();
                int reversed = ((Number) row.get("federation_reversed_amount")).intValue();
                totalPeriodic += amount;
                totalReversed += reversed;

                ReversementResponse.ReversementDetail detail = new ReversementResponse.ReversementDetail();
                detail.setContributionId((String) row.get("id"));
                detail.setCollectionDate(((java.sql.Date) row.get("collection_date")).toLocalDate());
                detail.setContributionAmount(amount);
                detail.setReversedAmount(reversed);
                details.add(detail);
            }

            ReversementResponse response = new ReversementResponse();
            response.setCollectivityId(collectivityId);
            response.setStartDate(startDate);
            response.setEndDate(endDate);
            response.setTotalPeriodicContributions(totalPeriodic);
            response.setReversalPercentage(10.0);
            response.setTotalReversedToFederation(totalReversed);
            response.setDetails(details);

            return response;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du calcul des reversements", e);
        }
    }

    private void validateMemberAndCollectivity(String memberId, String collectivityId) {
        String sql = "SELECT COUNT(*) FROM member WHERE id = ? AND collectivity_id = ? AND status = 'ACTIVE'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, memberId);
            stmt.setString(2, collectivityId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new BadRequestException("INVALID_MEMBER", "Member does not exist or does not belong to this collectivity");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de validation membre/collectivité", e);
        }
    }

    private void validatePaymentMode(String mode, String reference) {
        if (!"ESPECE".equals(mode) && (reference == null || reference.trim().isEmpty())) {
            throw new BadRequestException("MISSING_REFERENCE", "Transaction reference is required for bank transfer or mobile money");
        }
        if (!List.of("ESPECE", "VIREMENT_BANCAIRE", "MOBILE_MONEY").contains(mode)) {
            throw new BadRequestException("INVALID_PAYMENT_MODE", "Payment mode must be ESPECE, VIREMENT_BANCAIRE or MOBILE_MONEY");
        }
    }

    public ContributionResponse getContribution(String collectivityId, String contributionId) {
        Contribution contribution = contributionRepository.findById(contributionId);
        if (contribution == null || !contribution.getCollectivityId().equals(collectivityId)) {
            throw new ResourceNotFoundException("Contribution not found");
        }
        return ContributionResponse.fromEntity(contribution);
    }

    public int countContributions(String collectivityId, String type, String memberId,
                                  LocalDate startDate, LocalDate endDate) {
        try (Connection conn = DBConnection.getConnection()) {
            return contributionRepository.countByCollectivityId(conn, collectivityId, type, memberId, startDate, endDate);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du comptage des contributions", e);
        }
    }

    public List<ContributionResponse> listMemberContributions(String memberId, LocalDate startDate, LocalDate endDate,
                                                              int page, int limit) {
        try (Connection conn = DBConnection.getConnection()) {
            int offset = page * limit;
            List<Contribution> contributions = contributionRepository.findByMemberId(conn, memberId, startDate, endDate, offset, limit);
            List<ContributionResponse> responses = new ArrayList<>();
            for (Contribution c : contributions) {
                responses.add(ContributionResponse.fromEntity(c));
            }
            return responses;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la liste des contributions du membre", e);
        }
    }

    public int countMemberContributions(String memberId, LocalDate startDate, LocalDate endDate) {
        try (Connection conn = DBConnection.getConnection()) {
            return contributionRepository.countByMemberId(conn, memberId, startDate, endDate);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du comptage des contributions du membre", e);
        }
    }
}
