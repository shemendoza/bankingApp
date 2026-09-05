package com.bankApp.bankingApp.service;

import com.bankApp.bankingApp.util.DbConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BalanceService {

    public void updateBalance(int userId, double newBalance) {

        String sql = """
                UPDATE users
                SET balance = ?
                WHERE id = ?
                """;

        try (Connection connection = DbConnectionHelper.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDouble(1, newBalance);
            statement.setInt(2, userId);

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to update balance.",
                    e
            );
        }
    }
}