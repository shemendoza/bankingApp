package com.bankApp.bankingApp.service;

import com.bankApp.bankingApp.model.Transaction;
import com.bankApp.bankingApp.model.User;
import com.bankApp.bankingApp.util.DbConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransferService {

    public boolean transfer(
            User sender,
            String receiverMobile,
            double amount
    ) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Transfer amount must be greater than zero."
            );
        }

        if (receiverMobile == null
                || !receiverMobile.matches("\\d{11}")) {

            throw new IllegalArgumentException(
                    "Mobile number must contain exactly 11 digits."
            );
        }

        if (sender.getNumber().equals(receiverMobile)) {
            throw new IllegalArgumentException(
                    "You cannot transfer money to your own account."
            );
        }

        String findReceiverSql = """
                SELECT id, name, number, email, pin, balance, role
                FROM users
                WHERE number = ?
                FOR UPDATE
                """;

        String lockSenderSql = """
                SELECT balance
                FROM users
                WHERE id = ?
                FOR UPDATE
                """;

        String updateSenderSql = """
                UPDATE users
                SET balance = ?
                WHERE id = ?
                """;

        String updateReceiverSql = """
                UPDATE users
                SET balance = ?
                WHERE id = ?
                """;

        String insertTransactionSql = """
                INSERT INTO transactions
                (user_number, type, amount, date, user_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DbConnectionHelper.getConnection()) {

            try {

                // Start database transaction
                connection.setAutoCommit(false);

                /*
                 * Lock sender row and get the current balance
                 * directly from the database.
                 */
                double senderBalance;

                try (PreparedStatement statement =
                             connection.prepareStatement(lockSenderSql)) {

                    statement.setInt(1, sender.getId());

                    try (ResultSet resultSet =
                                 statement.executeQuery()) {

                        if (!resultSet.next()) {
                            throw new IllegalArgumentException(
                                    "Sender account could not be found."
                            );
                        }

                        senderBalance =
                                resultSet.getDouble("balance");
                    }
                }

                /*
                 * Check sender balance.
                 */
                if (amount > senderBalance) {

                    throw new IllegalArgumentException(
                            String.format(
                                    "Insufficient balance. " +
                                            "Your current balance is ₱%.2f.",
                                    senderBalance
                            )
                    );
                }

                /*
                 * Find and lock receiver.
                 */
                int receiverId;
                double receiverBalance;

                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     findReceiverSql
                             )) {

                    statement.setString(1, receiverMobile);

                    try (ResultSet resultSet =
                                 statement.executeQuery()) {

                        if (!resultSet.next()) {

                            throw new IllegalArgumentException(
                                    "No account was found for mobile number "
                                            + receiverMobile + "."
                            );
                        }

                        receiverId =
                                resultSet.getInt("id");

                        receiverBalance =
                                resultSet.getDouble("balance");
                    }
                }

                /*
                 * Calculate new balances.
                 */
                double newSenderBalance =
                        senderBalance - amount;

                double newReceiverBalance =
                        receiverBalance + amount;

                /*
                 * Update sender.
                 */
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     updateSenderSql
                             )) {

                    statement.setDouble(
                            1,
                            newSenderBalance
                    );

                    statement.setInt(
                            2,
                            sender.getId()
                    );

                    statement.executeUpdate();
                }

                /*
                 * Update receiver.
                 */
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     updateReceiverSql
                             )) {

                    statement.setDouble(
                            1,
                            newReceiverBalance
                    );

                    statement.setInt(
                            2,
                            receiverId
                    );

                    statement.executeUpdate();
                }

                LocalDateTime now =
                        LocalDateTime.now();

                /*
                 * Sender transaction.
                 *
                 * user_number = receiver's mobile number
                 */
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     insertTransactionSql
                             )) {

                    statement.setString(
                            1,
                            receiverMobile
                    );

                    statement.setString(
                            2,
                            "TRANSFER"
                    );

                    statement.setDouble(
                            3,
                            amount
                    );

                    statement.setTimestamp(
                            4,
                            java.sql.Timestamp.valueOf(now)
                    );

                    statement.setInt(
                            5,
                            sender.getId()
                    );

                    statement.executeUpdate();
                }

                /*
                 * Receiver transaction.
                 *
                 * user_number = sender's mobile number
                 */
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     insertTransactionSql
                             )) {

                    statement.setString(
                            1,
                            sender.getNumber()
                    );

                    statement.setString(
                            2,
                            "RECEIVED"
                    );

                    statement.setDouble(
                            3,
                            amount
                    );

                    statement.setTimestamp(
                            4,
                            java.sql.Timestamp.valueOf(now)
                    );

                    statement.setInt(
                            5,
                            receiverId
                    );

                    statement.executeUpdate();
                }

                /*
                 * Everything succeeded.
                 */
                connection.commit();

                /*
                 * Update sender's in-memory balance.
                 */
                sender.deductBalance(amount);

                return true;

            } catch (Exception e) {

                /*
                 * Something failed.
                 * Undo ALL database changes.
                 */
                connection.rollback();

                if (e instanceof IllegalArgumentException) {
                    throw (IllegalArgumentException) e;
                }

                throw new RuntimeException(
                        "Transfer failed.",
                        e
                );

            } finally {

                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Database error while processing transfer.",
                    e
            );
        }
    }
}