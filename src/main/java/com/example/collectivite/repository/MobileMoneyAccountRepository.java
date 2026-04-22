package com.example.collectivite.repository;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.entity.AccountType;
import com.example.collectivite.entity.MobileMoneyAccount;
import com.example.collectivite.entity.MobileMoneyService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MobileMoneyAccountRepository {
    private final DBConnection db;
    private final AccountRepository accountRepository;

    public MobileMoneyAccountRepository(DBConnection db, AccountRepository accountRepository) {
        this.db = db;
        this.accountRepository = accountRepository;
    }

    public MobileMoneyAccount save(MobileMoneyAccount mobileMoneyAccount) {
        accountRepository.save(mobileMoneyAccount);

        String sql = """
            INSERT INTO mobile_money_account
                (account_id, service, phone_number)
            VALUES (?, ?::mobile_money_service, ?)
            """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, mobileMoneyAccount.getId());
            ps.setString(2, mobileMoneyAccount.getMobileMoneyService().name());
            ps.setString(3, mobileMoneyAccount.getPhoneNumber());
            ps.executeUpdate();

            return mobileMoneyAccount;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving the mobile money account", e);
        }
    }

    public Optional<MobileMoneyAccount> findById(Integer id) {
        String sql = """
            SELECT a.id, a.collectivity_id, a.is_federation, a.account_type,
                   a.titular, a.balance, a.currency, a.creation_date,
                   mm.service, mm.numero_telephone
            FROM account a
            JOIN mobile_money_account mm ON mm.compte_id = a.id
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
            throw new RuntimeException("Error while searching for the mobile money account", e);
        }
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        String sql = """
            SELECT COUNT(1) FROM mobile_money_account
            WHERE phone_number = ?
            """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la verification du telephone", e);
        }

    }

    private MobileMoneyAccount mapRow(ResultSet rs) throws SQLException {
        MobileMoneyAccount mm = new MobileMoneyAccount();
        mm.setId(rs.getInt("id"));
        int collectivityId = rs.getInt("collectivity_id");
        if (!rs.wasNull()) {
            mm.setCollectivityId(collectivityId);
        }
        mm.setFederation(rs.getBoolean("is_federation"));
        mm.setAccountType(AccountType.valueOf(rs.getString("account_type")));
        mm.setTitular(rs.getString("titular"));
        mm.setBalance(rs.getBigDecimal("balance"));
        mm.setCurrency(rs.getString("currency"));
        mm.setCreationDate(rs.getDate("creation_date").toLocalDate());
        mm.setMobileMoneyService(
                MobileMoneyService.valueOf(rs.getString("service")));
        mm.setPhoneNumber(rs.getString("phone_number"));
        return mm;
    }
}
