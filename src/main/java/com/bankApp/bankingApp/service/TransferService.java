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

    private final BalanceService balanceService;
    private final TransactionService transactionService;

    public TransferService() {
        this.balanceService = new BalanceService();
        this.transactionService = new TransactionService();
    }

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
                SELECT id, balance
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

        try (Connection connection =
                     DbConnectionHelper.getConnection()) {

            try {

                /*
                 * Start database transaction.
                 */
                connection.setAutoCommit(false);

                /*
                 * Lock sender and get current balance.
                 */
                double senderBalance;

                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     lockSenderSql
                             )) {

                    statement.setInt(
                            1,
                            sender.getId()
                    );

                    try (ResultSet resultSet =
                                 statement.executeQuery()) {

                        if (!resultSet.next()) {

                            throw new IllegalArgumentException(
                                    "Sender account could not be found."
                            );
                        }

                        senderBalance =
                                resultSet.getDouble(
                                        "balance"
                                );
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

                    statement.setString(
                            1,
                            receiverMobile
                    );

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
                                resultSet.getDouble(
                                        "balance"
                                );
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
                 * Update sender using BalanceService.
                 */
                balanceService.updateBalance(
                        connection,
                        sender.getId(),
                        newSenderBalance
                );

                /*
                 * Update receiver using BalanceService.
                 */
                balanceService.updateBalance(
                        connection,
                        receiverId,
                        newReceiverBalance
                );

                /*
                 * Create one timestamp so both
                 * transactions have the same date.
                 */
                LocalDateTime now =
                        LocalDateTime.now();

                /*
                 * Sender transaction.
                 */
                Transaction senderTransaction =
                        new Transaction(
                                receiverMobile,
                                "TRANSFER",
                                amount,
                                now,
                                sender.getId()
                        );

                transactionService.saveTransaction(
                        connection,
                        senderTransaction
                );

                /*
                 * Receiver transaction.
                 */
                Transaction receiverTransaction =
                        new Transaction(
                                sender.getNumber(),
                                "RECEIVED",
                                amount,
                                now,
                                receiverId
                        );

                transactionService.saveTransaction(
                        connection,
                        receiverTransaction
                );

                /*
                 * Everything succeeded.
                 */
                connection.commit();

                /*
                 * Update sender's in-memory balance
                 * after successful database commit.
                 */
                sender.deductBalance(amount);

                /*
                 * Add sender transaction to memory.
                 */
                sender.addTransaction(
                        senderTransaction
                );

                return true;

            } catch (Exception e) {

                /*
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