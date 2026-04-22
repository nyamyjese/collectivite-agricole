package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.entity.ModePayment;
import com.example.collectivite.entity.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentRepository {

    private final DBConnection db;

    public PaymentRepository(DBConnection db) {
        this.db = db;
    }

    public Payment save(Payment payment) {
        String sql = """
            INSERT INTO payment
                (member_id, collectivity_id, amount, mode, reference, payment_date)
            VALUES (?, ?, ?, ?::mode_payment, ?, ?)
            RETURNING id
            """;
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, payment.getMemberId());
            ps.setInt(2, payment.getCollectivityId());
            ps.setBigDecimal(3, payment.getAmount());
            ps.setString(4, payment.getMode().name());
            ps.setString(5, payment.getReference());
            ps.setDate(6, Date.valueOf(payment.getPaymentDate()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                payment.setId(rs.getInt("id"));
            }
            return payment;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving payment", e);
        }
    }

    public Optional<Payment> findById(Integer id) {
        String sql = """
            SELECT id, member_id, collectivity_id, amount,
                   mode, reference, payment_date
            FROM payment
            WHERE id = ?
            """;
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding payment by id", e);
        }
    }

    public List<Payment> findByMemberId(Integer memberId) {
        String sql = """
            SELECT id, member_id, collectivity_id, amount,
                   mode, reference, payment_date
            FROM payment
            WHERE member_id = ?
            ORDER BY payment_date DESC
            """;
        List<Payment> payments = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                payments.add(mapRow(rs));
            }
            return payments;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding payments by member", e);
        }
    }

    public List<Payment> findByCollectivityId(Integer collectivityId) {
        String sql = """
            SELECT id, member_id, collectivity_id, amount,
                   mode, reference, payment_date
            FROM payment
            WHERE collectivity_id = ?
            ORDER BY payment_date DESC
            """;
        List<Payment> payments = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                payments.add(mapRow(rs));
            }
            return payments;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding payments by collectivity", e);
        }
    }

    public boolean existsByMemberAndCollectivity(Integer memberId, Integer collectivityId) {
        String sql = "SELECT COUNT(1) FROM payment WHERE member_id = ? AND collectivity_id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            ps.setInt(2, collectivityId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error checking payment existence", e);
        }
    }

    private Payment mapRow(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getInt("id"));
        payment.setMemberId(rs.getInt("member_id"));
        payment.setCollectivityId(rs.getInt("collectivity_id"));
        payment.setAmount(rs.getBigDecimal("amount"));
        payment.setMode(ModePayment.valueOf(rs.getString("mode")));
        payment.setReference(rs.getString("reference"));
        payment.setPaymentDate(rs.getDate("payment_date").toLocalDate());
        return payment;
    }
}