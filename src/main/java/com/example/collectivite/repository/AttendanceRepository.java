package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.*;

@Repository
public class AttendanceRepository {

    public void insert(Connection conn, String id, String activityId, String memberId,
                       String status, boolean isVisitor) throws SQLException {
        String sql = "INSERT INTO attendance (id, activity_id, member_id, status, is_visitor) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(2, activityId);
            stmt.setString(3, memberId);
            stmt.setString(4, status);
            stmt.setBoolean(5, isVisitor);
            stmt.executeUpdate();
        }
    }

    public Optional<String> getCurrentStatus(Connection conn, String activityId, String memberId) throws SQLException {
        String sql = "SELECT status FROM attendance WHERE activity_id = ? AND member_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, activityId);
            stmt.setString(2, memberId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? Optional.of(rs.getString("status")) : Optional.empty();
        }
    }

    public void updateStatus(Connection conn, String activityId, String memberId, String newStatus) throws SQLException {
        String sql = "UPDATE attendance SET status = ? WHERE activity_id = ? AND member_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setString(2, activityId);
            stmt.setString(3, memberId);
            stmt.executeUpdate();
        }
    }

    public List<Map<String, Object>> findByActivity(String activityId) {
        String sql = """
            SELECT a.id, a.status, a.is_visitor,
                   m.id AS member_id, m.first_names AS first_name, m.last_name, m.email, m.occupation
            FROM attendance a
            JOIN member m ON a.member_id = m.id
            WHERE a.activity_id = ?
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, activityId);
            ResultSet rs = stmt.executeQuery();
            List<Map<String, Object>> list = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getString("id"));
                map.put("status", rs.getString("status"));
                map.put("isVisitor", rs.getBoolean("is_visitor"));
                map.put("memberId", rs.getString("member_id"));
                map.put("firstName", rs.getString("first_name"));
                map.put("lastName", rs.getString("last_name"));
                map.put("email", rs.getString("email"));
                map.put("occupation", rs.getString("occupation"));
                list.add(map);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
