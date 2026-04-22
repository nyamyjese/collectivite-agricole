package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.entity.Contribution;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ContributionRepository {


     // Enregistre une nouvelle cotisation en utilisant la connexion fournie.
    public Contribution save(Connection conn, Contribution contribution) throws SQLException {
        String sql = "INSERT INTO contribution (id, member_id, collectivity_id, type, amount, payment_mode, " +
                "transaction_reference, collection_date, federation_reversed_amount, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contribution.getId());
            stmt.setString(2, contribution.getMemberId());
            stmt.setString(3, contribution.getCollectivityId());
            stmt.setString(4, contribution.getType());
            stmt.setInt(5, contribution.getAmount());
            stmt.setString(6, contribution.getPaymentMode());
            stmt.setString(7, contribution.getTransactionReference());
            stmt.setDate(8, Date.valueOf(contribution.getCollectionDate()));
            stmt.setInt(9, contribution.getFederationReversedAmount());
            stmt.setString(10, contribution.getStatus());

            stmt.executeUpdate();
        }
        return contribution;
    }


     //Recherche une contribution par son ID
    public Contribution findById(String id) {
        String sql = "SELECT * FROM contribution WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToContribution(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la contribution", e);
        }
        return null;
    }


     // Liste les contributions d'une collectivité avec filtres.
    public List<Contribution> findByCollectivityId(Connection conn, String collectivityId, String type,
                                                   String memberId, LocalDate startDate, LocalDate endDate,
                                                   int offset, int limit) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM contribution WHERE collectivity_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(collectivityId);

        if (type != null) {
            sql.append(" AND type = ?");
            params.add(type);
        }
        if (memberId != null) {
            sql.append(" AND member_id = ?");
            params.add(memberId);
        }
        if (startDate != null) {
            sql.append(" AND collection_date >= ?");
            params.add(Date.valueOf(startDate));
        }
        if (endDate != null) {
            sql.append(" AND collection_date <= ?");
            params.add(Date.valueOf(endDate));
        }
        sql.append(" ORDER BY collection_date DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        List<Contribution> contributions = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof Date) {
                    stmt.setDate(i + 1, (Date) param);
                } else if (param instanceof Integer) {
                    stmt.setInt(i + 1, (Integer) param);
                } else {
                    stmt.setString(i + 1, (String) param);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    contributions.add(mapRowToContribution(rs));
                }
            }
        }
        return contributions;
    }


     // Compte le nombre de contributions (pour pagination)
    public int countByCollectivityId(Connection conn, String collectivityId, String type,
                                     String memberId, LocalDate startDate, LocalDate endDate) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM contribution WHERE collectivity_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(collectivityId);

        if (type != null) {
            sql.append(" AND type = ?");
            params.add(type);
        }
        if (memberId != null) {
            sql.append(" AND member_id = ?");
            params.add(memberId);
        }
        if (startDate != null) {
            sql.append(" AND collection_date >= ?");
            params.add(Date.valueOf(startDate));
        }
        if (endDate != null) {
            sql.append(" AND collection_date <= ?");
            params.add(Date.valueOf(endDate));
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof Date) {
                    stmt.setDate(i + 1, (Date) param);
                } else {
                    stmt.setString(i + 1, (String) param);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

     //Récupère les détails des cotisations pour le calcul des reversements
    public List<Map<String, Object>> getReversementDetails(Connection conn, String collectivityId,
                                                           LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT id, collection_date, amount, federation_reversed_amount " +
                "FROM contribution " +
                "WHERE collectivity_id = ? AND collection_date BETWEEN ? AND ? " +
                "AND type IN ('PERIODIQUE_MENSUELLE', 'PERIODIQUE_ANNUELLE') " +
                "ORDER BY collection_date";

        List<Map<String, Object>> results = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("id", rs.getString("id"));
                    row.put("collection_date", rs.getDate("collection_date"));
                    row.put("amount", rs.getInt("amount"));
                    row.put("federation_reversed_amount", rs.getInt("federation_reversed_amount"));
                    results.add(row);
                }
            }
        }
        return results;
    }

    // Méthodes privées
    private Contribution mapRowToContribution(ResultSet rs) throws SQLException {
        Contribution c = new Contribution();
        c.setId(rs.getString("id"));
        c.setMemberId(rs.getString("member_id"));
        c.setCollectivityId(rs.getString("collectivity_id"));
        c.setType(rs.getString("type"));
        c.setAmount(rs.getInt("amount"));
        c.setPaymentMode(rs.getString("payment_mode"));
        c.setTransactionReference(rs.getString("transaction_reference"));
        c.setCollectionDate(rs.getDate("collection_date").toLocalDate());
        c.setFederationReversedAmount(rs.getInt("federation_reversed_amount"));
        c.setStatus(rs.getString("status"));
        return c;
    }

    public int countByMemberId(Connection conn, String memberId, LocalDate startDate, LocalDate endDate) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM contribution WHERE member_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(memberId);

        if (startDate != null) {
            sql.append(" AND collection_date >= ?");
            params.add(Date.valueOf(startDate));
        }
        if (endDate != null) {
            sql.append(" AND collection_date <= ?");
            params.add(Date.valueOf(endDate));
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof Date) {
                    stmt.setDate(i + 1, (Date) param);
                } else {
                    stmt.setString(i + 1, (String) param);
                }
            }
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }


    public List<Contribution> findByMemberId(Connection conn, String memberId,
                                             LocalDate startDate, LocalDate endDate,
                                             int offset, int limit) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM contribution WHERE member_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(memberId);

        if (startDate != null) {
            sql.append(" AND collection_date >= ?");
            params.add(Date.valueOf(startDate));
        }
        if (endDate != null) {
            sql.append(" AND collection_date <= ?");
            params.add(Date.valueOf(endDate));
        }
        sql.append(" ORDER BY collection_date DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        List<Contribution> contributions = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof Date) {
                    stmt.setDate(i + 1, (Date) param);
                } else if (param instanceof Integer) {
                    stmt.setInt(i + 1, (Integer) param);
                } else {
                    stmt.setString(i + 1, (String) param);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    contributions.add(mapRowToContribution(rs));
                }
            }
        }
        return contributions;
    }
}