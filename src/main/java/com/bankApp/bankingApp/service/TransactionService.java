package com.bankApp.bankingApp.service;

import com.bankApp.bankingApp.model.Transaction;
import com.bankApp.bankingApp.util.DbConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    public void saveTransaction(
            Transaction transaction
    ) {

        try (Connection connection =
                     DbConnectionHelper.getConnection()) {

            saveTransaction(
                    connection,
                    transaction
            );

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to save transaction.",
                    e
            );
        }
    }

    public void saveTransaction(
            Connection connection,
            Transaction transaction
    ) throws SQLException {

        String sql = """
                INSERT INTO transactions
                (user_number, type, amount, date, user_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    transaction.getUserNumber()
            );

            statement.setString(
                    2,
                    transaction.getType()
            );

            statement.setDouble(
                    3,
                    transaction.getAmount()
            );

            statement.setTimestamp(
                    4,
                    Timestamp.valueOf(
                            transaction.getDate()
                    )
            );

            statement.setInt(
                    5,
                    transaction.getUserId()
            );

            statement.executeUpdate();
        }
    }

    public List<Transaction> getTransactions(
            int userId
    ) {

        List<Transaction> transactions =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    user_number,
                    type,
                    amount,
                    date,
                    user_id
                FROM transactions
                WHERE user_id = ?
                ORDER BY date DESC
                """;

        try (Connection connection =
                     DbConnectionHelper.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    userId
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Timestamp timestamp =
                            resultSet.getTimestamp("date");

                    Transaction transaction =
                            new Transaction(
                                    resultSet.getInt("id"),
                                    resultSet.getString(
                                            "user_number"
                                    ),
                                    resultSet.getString(
                                            "type"
                                    ),
                                    resultSet.getDouble(
                                            "amount"
                                    ),
                                    timestamp.toLocalDateTime(),
                                    resultSet.getInt(
                                            "user_id"
                                    )
                            );

                    transactions.add(transaction);
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to retrieve transactions.",
                    e
            );
        }

        return transactions;
    }

    public List<Transaction> getAllTransactions() {

        List<Transaction> transactions =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    user_number,
                    type,
                    amount,
                    date,
                    user_id
                FROM transactions
                ORDER BY date DESC
                """;

        try (Connection connection =
                     DbConnectionHelper.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Timestamp timestamp =
                        resultSet.getTimestamp("date");

                Transaction transaction =
                        new Transaction(
                                resultSet.getInt("id"),
                                resultSet.getString(
                                        "user_number"
                                ),
                                resultSet.getString(
                                        "type"
                                ),
                                resultSet.getDouble(
                                        "amount"
                                ),
                                timestamp.toLocalDateTime(),
                                resultSet.getInt(
                                        "user_id"
                                )
                        );

                transactions.add(transaction);
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to retrieve all transactions.",
                    e
            );
        }

        return transactions;
    }
}