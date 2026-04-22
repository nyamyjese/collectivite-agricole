package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.entity.Membership;
import com.example.collectivite.entity.Poste;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MembershipRepository {

    private final DBConnection db;

    public MembershipRepository(DBConnection db) {
        this.db = db;
    }

    public Membership save(Membership membership) {
        String sql = """
            INSERT INTO membership
                (membre_id, collectivity_id, poste, start_date, end_date)
            VALUES (?, ?, ?::poste, ?, ?)
            RETURNING id
            """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, membership.getMemberId());
            ps.setInt(2, membership.getCollectivityId());
            ps.setString(3, membership.getPoste().name());
            ps.setDate(4, Date.valueOf(membership.getStartDate()));
            if (membership.getEndDate() != null) {
                ps.setDate(5, Date.valueOf(membership.getEndDate()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                membership.setId(rs.getInt("id"));
            }
            return membership;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving membership", e);
        }
    }

    public Optional<Membership> findActiveByMember(Integer memberId) {
        String sql = """
            SELECT id, membre_id, collectivity_id, poste, start_date, end_date
            FROM membership
            WHERE membre_id = ? AND end_date IS NULL
            """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error searching for membership", e);
        }
    }

    public List<Membership> findActiveByCollectivity(Integer collectivityId) {
        String sql = """
            SELECT id, membre_id, collectivity_id, poste, start_date, end_date
            FROM membership
            WHERE collectivity_id = ? AND end_date IS NULL
            """;

        List<Membership> result = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, collectivityId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Error listing memberships", e);
        }
    }

    public int countActiveByCollectivity(Integer collectivityId) {
        String sql = """
            SELECT COUNT(id) FROM membership
            WHERE collectivity_id = ? AND end_date IS NULL
            """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException("Error counting members", e);
        }
    }

    public boolean isPositionOccupied(Integer collectivityId, Poste poste) {
        String sql = """
            SELECT COUNT(id) FROM federation.appartenance
            WHERE collectivity_id = ? AND poste = ?::poste
              AND end_date IS NULL
            """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, collectivityId);
            ps.setString(2, poste.name());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error checking position", e);
        }
    }

    private Membership mapRow(ResultSet rs) throws SQLException {
        Membership m = new Membership();
        m.setId(rs.getInt("id"));
        m.setMemberId(rs.getInt("membre_id"));
        m.setCollectivityId(rs.getInt("collectivity_id"));
        m.setPoste(Poste.valueOf(rs.getString("poste")));
        m.setStartDate(rs.getDate("start_date").toLocalDate());
        Date endDate = rs.getDate("end_date");
        if (endDate != null) {
            m.setEndDate(endDate.toLocalDate());
        }
        return m;
    }
}
