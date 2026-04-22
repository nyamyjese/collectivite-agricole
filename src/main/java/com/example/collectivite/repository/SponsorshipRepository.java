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
            INSERT INTO federation.parrainage
                (candidate_id, sponsorship, target_community_id,
                 sponsor_community_id, relation, referral_date)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id
            """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sponsorship.getCandidateId());
            ps.setInt(2, sponsorship.getSponsorId());
            ps.setInt(3, sponsorship.getTargetCommunityId());
            ps.setInt(4, sponsorship.getSponsorCommunityId());
            ps.setString(5, sponsorship.getRelation());
            ps.setDate(6, Date.valueOf(sponsorship.getReferralDate()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sponsorship.setId(rs.getInt("id"));
            }
            return sponsorship;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving sponsorship", e);
        }
    }

    public List<Sponsorship> findByCandidateAndTargetCollectivity(Integer candidateId,
                                                                  Integer targetCollectivityId) {
        String sql = """
            SELECT id, candidate_id, sponsorship_id, target_community_id,
                   sponsor_community_id, relation, referral_date
            FROM parrainage
            WHERE candidate_id = ? AND target_community_id = ?
            """;

        List<Sponsorship> result = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, candidateId);
            ps.setInt(2, targetCollectivityId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Error searching for sponsorships", e);
        }
    }

    public int countSponsorsInTargetCollectivity(Integer candidateId,
                                                 Integer targetCollectivityId) {
        String sql = """
            SELECT COUNT(*) FROM sponsorship
            WHERE candidate_id = ?
              AND target_community_id = ?
              AND sponsor_community_id = ?
            """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, candidateId);
            ps.setInt(2, targetCollectivityId);
            ps.setInt(3, targetCollectivityId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException("Error counting sponsors", e);
        }
    }

    private Sponsorship mapRow(ResultSet rs) throws SQLException {
        Sponsorship s = new Sponsorship();
        s.setId(rs.getInt("id"));
        s.setCandidateId(rs.getInt("candidate_id"));
        s.setSponsorId(rs.getInt("sponsorship_id"));
        s.setTargetCommunityId(rs.getInt("target_community_id"));
        s.setSponsorCommunityId(rs.getInt("sponsorship_community_id"));
        s.setRelation(rs.getString("relation"));
        s.setReferralDate(rs.getDate("referral_date").toLocalDate());
        return s;
    }
}