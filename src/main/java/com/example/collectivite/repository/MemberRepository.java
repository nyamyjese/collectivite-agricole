package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.model.Gender;
import com.example.collectivite.model.Member;
import com.example.collectivite.model.MemberStatus;

import java.sql.*;
import java.util.Optional;

public class MemberRepository {

    private final DBConnection db;

    public MemberRepository(DBConnection db) {
        this.db = db;
    }

    public Member save(Member member) {
        String sql = """
            INSERT INTO member
                (last_name, first_names, birth_date, gender,
                 address, profession, phone, email,
                 membership_date, status)
            VALUES (?, ?, ?, ?::gender,
                    ?, ?, ?, ?,
                    ?, ?::member_status)
            RETURNING id
            """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, member.getName());
            ps.setString(2, member.getFirstName());
            ps.setDate(3, Date.valueOf(member.getBirthDate()));
            ps.setString(4, member.getGender().name());
            ps.setString(5, member.getAdress());
            ps.setString(6, member.getProfession());
            ps.setString(7, member.getPhone());
            ps.setString(8, member.getEmail());
            ps.setDate(9, Date.valueOf(member.getJoinDate()));
            ps.setString(10, member.getStatus().name());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                member.setId(rs.getInt("id"));
            }
            return member;

        } catch (SQLException e) {
            throw new RuntimeException("Error while saving member", e);
        }
    }

    public Optional<Member> findById(Integer id) {
        String sql = """
            SELECT id, last_name, first_names, birth_date, gender,
                   address, profession, phone, email,
                   membership_date, status
            FROM member
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
            throw new RuntimeException("Error while finding member", e);
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(id) FROM member WHERE email = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error while checking email existence", e);
        }
    }

    public int getMembershipDurationInMonths(Integer memberId) {
        String sql = """
            SELECT EXTRACT(YEAR FROM AGE(NOW(), membership_date)) * 12
                 + EXTRACT(MONTH FROM AGE(NOW(), membership_date)) AS months
            FROM member
            WHERE id = ?
            """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("months");
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error while calculating membership duration", e);
        }
    }

    private Member mapRow(ResultSet rs) throws SQLException {
        Member m = new Member();

        m.setId(rs.getInt("id"));
        m.setName(rs.getString("last_name"));
        m.setFirstName(rs.getString("first_names"));
        m.setBirthDate(rs.getDate("birth_date").toLocalDate());
        m.setGender(Gender.valueOf(rs.getString("gender")));
        m.setAdress(rs.getString("address"));
        m.setProfession(rs.getString("profession"));
        m.setPhone(rs.getString("phone"));
        m.setEmail(rs.getString("email"));
        m.setJoinDate(rs.getDate("join_date").toLocalDate());
        m.setStatus(MemberStatus.valueOf(rs.getString("status")));

        return m;
    }
}
