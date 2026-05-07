package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
public class ActivityRepository {

    public void insertActivity(Connection conn, String id, String collectivityId, String label,
                               String activityType, List<String> occupations,
                               LocalDate executiveDate, String recurrenceRule) throws SQLException {
        String sql = "INSERT INTO activity (id, collectivity_id, label, activity_type, executive_date, recurrence_rule) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(2, collectivityId);
            stmt.setString(3, label);
            stmt.setString(4, activityType);
            if (executiveDate != null) stmt.setDate(5, Date.valueOf(executiveDate));
            else stmt.setNull(5, Types.DATE);
            stmt.setString(6, recurrenceRule);
            stmt.executeUpdate();
        }
        if (occupations != null && !occupations.isEmpty()) {
            String occSql = "INSERT INTO activity_occupation (activity_id, occupation) VALUES (?,?)";
            try (PreparedStatement stmt = conn.prepareStatement(occSql)) {
                for (String occ : occupations) {
                    stmt.setString(1, id);
                    stmt.setString(2, occ);
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
        }
    }

    public List<Map<String, Object>> findByCollectivity(String collectivityId) {
        String sql = """
            SELECT a.id, a.label, a.activity_type, a.executive_date, a.recurrence_rule,
                   ARRAY_AGG(ao.occupation) AS occupations
            FROM activity a
            LEFT JOIN activity_occupation ao ON a.id = ao.activity_id
            WHERE a.collectivity_id = ?
            GROUP BY a.id
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            List<Map<String, Object>> list = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getString("id"));
                map.put("label", rs.getString("label"));
                map.put("activityType", rs.getString("activity_type"));
                map.put("executiveDate", rs.getDate("executive_date") != null ? rs.getDate("executive_date").toLocalDate() : null);
                map.put("recurrenceRule", rs.getString("recurrence_rule"));
                Array arr = rs.getArray("occupations");
                List<String> occs = new ArrayList<>();
                if (arr != null) {
                    String[] items = (String[]) arr.getArray();
                    occs = Arrays.asList(items);
                }
                map.put("occupations", occs);
                list.add(map);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur récupération activités", e);
        }
    }

    public boolean existsInCollectivity(String activityId, String collectivityId) {
        String sql = "SELECT COUNT(*) FROM activity WHERE id = ? AND collectivity_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, activityId);
            stmt.setString(2, collectivityId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}