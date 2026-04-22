package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.entity.Collectivity;

import java.math.BigDecimal;
import java.sql.*;

public class CollectivityRepository {

    private final DBConnection db;

    public CollectivityRepository(DBConnection db) {
        this.db = db;
    }

    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(1) FROM collectivity WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error checking collectivity existence", e);
        }
    }

    public BigDecimal getAnnualContribution(Integer id) {
        String sql = "SELECT annual_contribution FROM collectivity WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("annual_contribution");
            }
            return BigDecimal.ZERO;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching annual contribution", e);
        }
    }

    public Collectivity save(Collectivity collectivity) {
        String sql = """
            INSERT INTO collectivity
                (unique_number, unique_name, specialty, creation_date,
                 city, annual_contribution, authorization_date)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            RETURNING id
            """;
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivity.getUniqueNumber());
            ps.setString(2, collectivity.getUniqueName());
            ps.setString(3, collectivity.getSpecialty());
            ps.setDate(4, Date.valueOf(collectivity.getCreationDate()));
            ps.setString(5, collectivity.getCity());
            ps.setBigDecimal(6, collectivity.getAnnualContribution());
            ps.setDate(7, Date.valueOf(collectivity.getAuthorizationDate()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                collectivity.setId(rs.getInt("id"));
            }
            return collectivity;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving collectivity", e);
        }
    }

    public boolean existsByUniqueNumber(String uniqueNumber) {
        String sql = """
        SELECT COUNT(id, unique_number, unique_name , speciality , creation_date,
               city , annual_contribution , authorization_date) 
        FROM collectivity 
        WHERE unique_number = ?     
        """;
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, uniqueNumber);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error checking unique number", e);
        }
    }

    public boolean existsByUniqueName(String uniqueName) {
        String sql = "SELECT COUNT(*) FROM collectivity WHERE unique_name = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, uniqueName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error checking unique name", e);
        }
    }
}