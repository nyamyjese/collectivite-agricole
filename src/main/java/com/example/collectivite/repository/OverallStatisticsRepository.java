package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
public class OverallStatisticsRepository {

    public List<Map<String, Object>> getOverallStats(LocalDate from, LocalDate to) {
        String sql = """
            SELECT
                col.id AS collectivity_id,
                col.unique_name AS name,
                col.unique_number::int AS number,
                COUNT(DISTINCT m.id) AS total_active,
                COUNT(DISTINCT CASE WHEN m.join_date BETWEEN ? AND ? THEN m.id END) AS new_members,
                SUM(CASE WHEN COALESCE(member_paid.total, 0) >= col.annual_contribution THEN 1 ELSE 0 END) AS up_to_date,
                CASE WHEN COUNT(DISTINCT m.id) > 0
                     THEN (SUM(CASE WHEN COALESCE(member_paid.total,0) >= col.annual_contribution THEN 1 ELSE 0 END) * 100.0 / COUNT(DISTINCT m.id))
                     ELSE 0
                END AS due_percentage
            FROM collectivite col
            JOIN member m ON col.id = m.collectivity_id AND m.status = 'ACTIVE'
            LEFT JOIN LATERAL (
                SELECT c.member_id, SUM(c.amount) AS total
                FROM contribution c
                WHERE c.member_id = m.id AND c.collection_date BETWEEN ? AND ?
                GROUP BY c.member_id
            ) member_paid ON member_paid.member_id = m.id
            GROUP BY col.id, col.unique_name, col.unique_number
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(from));
            stmt.setDate(2, Date.valueOf(to));
            stmt.setDate(3, Date.valueOf(from));
            stmt.setDate(4, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            List<Map<String, Object>> result = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("collectivityId", rs.getString("collectivity_id"));
                map.put("name", rs.getString("name"));
                map.put("number", rs.getInt("number"));
                map.put("newMembers", rs.getInt("new_members"));
                map.put("duePercentage", rs.getDouble("due_percentage"));
                result.add(map);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error computing overall statistics", e);
        }
    }
}
