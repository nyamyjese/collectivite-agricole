package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.entity.MembershipFee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipFeeRepository {
    private final DBConnection db;

    public MembershipFeeRepository(DBConnection db) {
        this.db = db;
    }

    public MembershipFee save(MembershipFee fee) {
        String sql = "INSERT INTO membership_fee (collectivity_id, type, amount, description) VALUES (?, ?, ?::fee_type, ?) RETURNING id";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fee.getCollectivityId());
            ps.setString(2, fee.getType());
            ps.setBigDecimal(3, fee.getAmount());
            ps.setString(4, fee.getDescription());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fee.setId(rs.getString("id"));
            }
            return fee;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving membership fee", e);
        }
    }

    public List<MembershipFee> findByCollectivityId(String collectivityId) {
        String sql = "SELECT id, collectivity_id, type, amount, description FROM membership_fee WHERE collectivity_id = ?";
        List<MembershipFee> fees = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                fees.add(mapRow(rs));
            }
            return fees;
        } catch (SQLException e) {
            throw new RuntimeException("Error listing membership fees", e);
        }
    }

    private MembershipFee mapRow(ResultSet rs) throws SQLException {
        MembershipFee fee = new MembershipFee();
        fee.setId(rs.getString("id"));
        fee.setCollectivityId(rs.getString("collectivity_id"));
        fee.setType(rs.getString("type"));
        fee.setAmount(rs.getBigDecimal("amount"));
        fee.setDescription(rs.getString("description"));
        return fee;
    }
}