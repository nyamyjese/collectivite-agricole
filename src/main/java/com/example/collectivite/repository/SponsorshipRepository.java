package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.entity.Sponsorship;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SponsorshipRepository {
    private final DBConnection db;

    public SponsorshipRepository(DBConnection db) {
        this.db = db;
    }

    public Sponsorship save(Sponsorship sponsorship) {
        String sql = """
            INSERT INTO sponsorship
                (candidate_id, sponsor_id, target_community_id,
                 sponsor_community_id, relation, referral_date)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id
            """;
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sponsorship.getCandidateId());
            ps.setString(2, sponsorship.getSponsorId());
            ps.setString(3, sponsorship.getTargetCommunityId());
            ps.setString(4, sponsorship.getSponsorCommunityId());
            ps.setString(5, sponsorship.getRelation());
            ps.setDate(6, Date.valueOf(sponsorship.getReferralDate()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sponsorship.setId(rs.getString("id"));
            }
            return sponsorship;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving sponsorship", e);
        }
    }

    public List<Sponsorship> findByCandidateAndTargetCollectivity(String candidateId, String targetCollectivityId) {
        String sql = """
            SELECT id, candidate_id, sponsor_id, target_community_id,
                   sponsor_community_id, relation, referral_date
            FROM sponsorship
            WHERE candidate_id = ? AND target_community_id = ?
            """;
        List<Sponsorship> result = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, candidateId);
            ps.setString(2, targetCollectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error searching for sponsorships", e);
        }
    }

    public int countSponsorsInTargetCollectivity(String candidateId, String targetCollectivityId) {
        String sql = """
            SELECT COUNT(1) FROM sponsorship
            WHERE candidate_id = ? AND target_community_id = ? AND sponsor_community_id = ?
            """;
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, candidateId);
            ps.setString(2, targetCollectivityId);
            ps.setString(3, targetCollectivityId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error counting sponsors", e);
        }
    }

    private Sponsorship mapRow(ResultSet rs) throws SQLException {
        Sponsorship s = new Sponsorship();
        s.setId(rs.getString("id"));
        s.setCandidateId(rs.getString("candidate_id"));
        s.setSponsorId(rs.getString("sponsor_id"));
        s.setTargetCommunityId(rs.getString("target_community_id"));
        s.setSponsorCommunityId(rs.getString("sponsor_community_id"));
        s.setRelation(rs.getString("relation"));
        s.setReferralDate(rs.getDate("referral_date").toLocalDate());
        return s;
    }
}