package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.entity.AccountType;
import com.example.collectivite.entity.Bank;
import com.example.collectivite.entity.BankAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class BankAccountRepository {
    private final DBConnection db;
    private final AccountRepository accountRepository;

    public  BankAccountRepository(DBConnection db, AccountRepository accountRepository) {
        this.db = db;
        this.accountRepository = accountRepository;
    }

    public BankAccount save(BankAccount bankAccount) {
        accountRepository.save(bankAccount);

        String sql = """
            INSERT INTO bank_account
                (account_id, bank, account_number)
            VALUES (?, ?::bank, ?)
            """;
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bankAccount.getId());
            ps.setString(2, bankAccount.getBank().name());
            ps.setString(3, bankAccount.getAccountNumber());
            ps.executeUpdate();

            return bankAccount;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving the bank account", e);
        }
    }

    public Optional<BankAccount> findById(Integer id) {
        String sql = """
            SELECT id, collectivity_id, is_federation, account_type,
                   titular, balance, currency, creation_date,
                   bank, account_number
            FROM account a
            JOIN bank_account bk ON bk.id = a.id
            WHERE a.id = ?
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
            throw new RuntimeException("Error while searching for the bank account", e);
        }
    }

    public boolean existsByAccountNumber(String accountNumber) {
        String sql = """
            SELECT COUNT(1) FROM bank_account
            WHERE account_number = ?
            """;
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error verifying the account number", e);
        }
    }

    private BankAccount mapRow(ResultSet rs) throws SQLException {
        BankAccount bk = new BankAccount();
        bk.setId(rs.getInt("id"));
        int collectivityId = rs.getInt("collectivity_id");
        if (!rs.wasNull()) {
            bk.setCollectivityId(collectivityId);
        }
        bk.setFederation(rs.getBoolean("is_federation"));
        bk.setAccountType(AccountType.valueOf(rs.getString("account_type")));
        bk.setTitular(rs.getString("titular"));
        bk.setBalance(rs.getBigDecimal("balance"));
        bk.setCurrency(rs.getString("currency"));
        bk.setCreationDate(rs.getDate("creation_date").toLocalDate());
        bk.setBank(Bank.valueOf(rs.getString("bank")));
        bk.setAccountNumber(rs.getString("account_number"));
        return bk;
    }
}
