package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.entity.Account;
import com.example.collectivite.entity.AccountType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository {
    private final DBConnection db;

    public AccountRepository(DBConnection db) {
        this.db = db;
    }

    public Account save(Account account) {
        String sql = """
                INSERT INTO account
                    (collectivity_id, is_federation, account_type ,
                    titular , balance , currency , creation_date)
                VALUES (? , ? , ?::account_type, ? , ? , ? , ? )
                RETURNING id
                """;
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if(account.getCollectivityId() != null) {
                ps.setInt(1, account.getCollectivityId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setBoolean(2, account.isFederation());
            ps.setString(3, account.getAccountType().name());
            ps.setString(4, account.getTitular());
            ps.setBigDecimal(5,account.getBalance());
            ps.setString(6,account.getCurrency());
            ps.setDate(7, Date.valueOf(account.getCreationDate()));

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                account.setId(rs.getInt("id"));
            }
            return account;
        }
        catch (SQLException e) {
            throw new RuntimeException("Error saving the account", e);
        }
    }

    public Optional<Account> findById(Integer id) {
        String sql = """
                SELECT id , collectivity_id , is_federation, account_type, 
                    titular , balance , currency , creation_date
                FROM account 
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
            throw new RuntimeException("Error while searching for the account", e);
        }
    }

    public List<Account> findByCollectivite(Integer collectiviteId) {
        String sql = """
            SELECT id, collectivity_id, is_federation, account_type,
                   titular, balance, currency, creation_date
            FROM account
            WHERE collectivity_id = ?
            ORDER BY account_type, id
            """;

        List<Account> result = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, collectiviteId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Error while listing accounts", e);
        }
    }

    public List<Account> findByFederation() {
        String sql = """
            SELECT id, collectivity_id, is_federation, account_type,
                   titular, balance, currency, creation_date
            FROM account
            WHERE is_federation = TRUE
            ORDER BY account_type, id
            """;

        List<Account> result = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Error listing federation accounts", e);
        }
    }

    public boolean fundExist(Integer collectivityId, boolean isFederation) {
        String sql;
        if (isFederation) {
            sql = """
                SELECT COUNT(1) FROM account
                WHERE is_federation = TRUE
                  AND account_type = 'CASH'
                """;
        } else {
            sql = """
                SELECT COUNT(1) FROM federation.compte
                WHERE collectivity_id = ?
                  AND account_type = 'CASH'
                """;
        }

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (!isFederation) {
                ps.setInt(1, collectivityId);
            }
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error during checkout", e);
        }
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("id"));

        if (rs.getObject("collectivity_id") != null) {
            account.setCollectivityId(rs.getInt("collectivity_id"));
        }

        account.setFederation(rs.getBoolean("is_federation"));

        String accountTypeStr = rs.getString("account_type");
        if (accountTypeStr != null) {
            account.setAccountType(AccountType.valueOf(accountTypeStr.toUpperCase()));
        }

        account.setTitular(rs.getString("titular"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setCurrency(rs.getString("currency"));
        account.setCreationDate(rs.getDate("creation_date").toLocalDate());

        return account;
    }

}
