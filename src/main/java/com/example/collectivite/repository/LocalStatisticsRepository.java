package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
public class LocalStatisticsRepository {

    public List<Map<String, Object>> getMemberStats(String collectivityId, LocalDate from, LocalDate to) {
        String sql = """
            SELECT
                m.id AS member_id,
                m.first_name AS first_name,
                m.last_name,
                m.email,
                m.occupation,
                COALESCE(SUM(c.amount), 0) AS earned_amount,
                col.annual_contribution - COALESCE(SUM(c.amount), 0) AS unpaid_amount
            FROM member m
            JOIN collectivite col ON m.collectivity_id = col.id
            LEFT JOIN contribution c ON m.id = c.member_id
                AND c.collection_date BETWEEN ? AND ?
            WHERE m.collectivity_id = ? AND m.status = 'ACTIVE'
            GROUP BY m.id, m.first_name, m.last_name, m.email, m.occupation, col.annual_contribution
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(from));
            stmt.setDate(2, Date.valueOf(to));
            stmt.setString(3, collectivityId);
            ResultSet rs = stmt.executeQuery();
            List<Map<String, Object>> result = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("memberId", rs.getString("member_id"));
                map.put("firstName", rs.getString("first_name"));
                map.put("lastName", rs.getString("last_name"));
                map.put("email", rs.getString("email"));
                map.put("occupation", rs.getString("occupation"));
                map.put("earnedAmount", rs.getDouble("earned_amount"));
                map.put("unpaidAmount", rs.getDouble("unpaid_amount"));
                result.add(map);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error computing local statistics", e);
        }
    }
}
