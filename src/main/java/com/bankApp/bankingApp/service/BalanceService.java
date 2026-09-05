package com.bankApp.bankingApp.service;

import com.bankApp.bankingApp.util.DbConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BalanceService {

    public void updateBalance(int userId, double newBalance) {

        try (Connection connection =
                     DbConnectionHelper.getConnection()) {

            updateBalance(
                    connection,
                    userId,
                    newBalance
            );

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to update balance.",
                    e
            );
        }
    }

    public void updateBalance(
            Connection connection,
            int userId,
            double newBalance
    ) throws SQLException {

        String sql = """
                UPDATE users
                SET balance = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDouble(1, newBalance);
            statement.setInt(2, userId);

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new IllegalArgumentException(
                        "User account could not be found."
                );
            }
        }
    }
}