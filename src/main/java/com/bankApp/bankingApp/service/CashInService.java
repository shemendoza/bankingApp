package com.bankApp.bankingApp.service;

import com.bankApp.bankingApp.model.Transaction;
import com.bankApp.bankingApp.model.User;
import com.bankApp.bankingApp.util.DbConnectionHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CashInService {

    private final BalanceService balanceService;
    private final TransactionService transactionService;

    public CashInService() {
        this.balanceService = new BalanceService();
        this.transactionService = new TransactionService();
    }

    public void cashIn(User user, double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Cash-in amount must be greater than zero."
            );
        }

        try (Connection connection =
                     DbConnectionHelper.getConnection()) {

            try {

                /*
                 * Start database transaction.
                 */
                connection.setAutoCommit(false);

                /*
                 * Calculate new balance.
                 */
                double newBalance =
                        user.getBalance() + amount;

                /*
                 * Update balance using BalanceService.
                 */
                balanceService.updateBalance(
                        connection,
                        user.getId(),
                        newBalance
                );

                /*
                 * Create transaction.
                 */
                Transaction transaction =
                        new Transaction(
                                user.getNumber(),
                                "CASH_IN",
                                amount,
                                LocalDateTime.now(),
                                user.getId()
                        );

                /*
                 * Save transaction using
                 * TransactionService.
                 */
                transactionService.saveTransaction(
                        connection,
                        transaction
                );

                /*
                 * Commit both operations.
                 */
                connection.commit();

                /*
                 * Update in-memory User object
                 * only after successful commit.
                 */
                user.addBalance(amount);

                user.addTransaction(transaction);

            } catch (Exception e) {

                /*
                 * Undo all database changes.
                 */
                connection.rollback();

                if (e instanceof IllegalArgumentException) {
                    throw (IllegalArgumentException) e;
                }

                throw new RuntimeException(
                        "Cash-in failed.",
                        e
                );

            } finally {

                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Database error while processing cash-in.",
                    e
            );
        }
    }
}