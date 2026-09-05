package com.bankApp.bankingApp.service;

import com.bankApp.bankingApp.model.User;
import com.bankApp.bankingApp.util.DbConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {

    public String register(User user) {

        String checkEmailSql = """
                SELECT id
                FROM users
                WHERE email = ?
                """;

        String checkMobileSql = """
                SELECT id
                FROM users
                WHERE number = ?
                """;

        String insertSql = """
                INSERT INTO users
                (name, number, email, pin, balance, role)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DbConnectionHelper.getConnection()) {

            // Check email
            try (PreparedStatement statement =
                         connection.prepareStatement(checkEmailSql)) {

                statement.setString(1, user.getEmail());

                try (ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {
                        return "EMAIL_EXISTS";
                    }
                }
            }

            // Check mobile number
            try (PreparedStatement statement =
                         connection.prepareStatement(checkMobileSql)) {

                statement.setString(1, user.getNumber());

                try (ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {
                        return "MOBILE_EXISTS";
                    }
                }
            }

            // Insert user
            try (PreparedStatement statement =
                         connection.prepareStatement(insertSql)) {

                statement.setString(1, user.getName());
                statement.setString(2, user.getNumber());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getPin());
                statement.setDouble(5, user.getBalance());
                statement.setString(6, user.getRole());

                statement.executeUpdate();

                return "SUCCESS";
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to register user.",
                    e
            );
        }
    }


    // LOGIN
    public User login(String mobileNumber, String pin) {

        String sql = """
                SELECT id, name, number, email, pin, balance, role
                FROM users
                WHERE number = ?
                """;

        try (Connection connection = DbConnectionHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, mobileNumber);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    String storedPin = resultSet.getString("pin");

                    if (storedPin.equals(pin)) {

                        return new User(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("number"),
                                resultSet.getString("email"),
                                resultSet.getString("pin"),
                                resultSet.getDouble("balance"),
                                resultSet.getString("role")
                        );
                    }
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to login.",
                    e
            );
        }

        return null;
    }


    // FORGOT MPIN
    public String getMpinByEmail(String email) {

        String sql = """
                SELECT pin
                FROM users
                WHERE email = ?
                """;

        try (Connection connection = DbConnectionHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getString("pin");
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to retrieve MPIN.",
                    e
            );
        }

        return null;
    }

    public boolean changeMpin(
            int userId,
            String identifier,
            String newMpin
    ) {

        String sql = """
            UPDATE users
            SET pin = ?
            WHERE id = ?
            AND (number = ? OR email = ?)
            """;

        try (Connection connection = DbConnectionHelper.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, newMpin);
            statement.setInt(2, userId);
            statement.setString(3, identifier);
            statement.setString(4, identifier);

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to change MPIN.",
                    e
            );
        }
    }
    
}