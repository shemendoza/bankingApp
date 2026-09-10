package com.bankApp.bankingApp.service;

import com.bankApp.bankingApp.model.User;
import com.bankApp.bankingApp.util.DbConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthService {

    public String register(User user) {

        String checkUserSql = """
                SELECT email, number
                FROM users
                WHERE email = ?
                OR number = ?
                """;

        String insertSql = """
                INSERT INTO users
                (name, number, email, pin, balance, role)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DbConnectionHelper.getConnection()) {

            // Check existing email or mobile number
            try (PreparedStatement statement =
                         connection.prepareStatement(checkUserSql)) {

                statement.setString(1, user.getEmail());
                statement.setString(2, user.getNumber());

                try (ResultSet resultSet = statement.executeQuery()) {

                    while (resultSet.next()) {

                        String existingEmail =
                                resultSet.getString("email");

                        String existingNumber =
                                resultSet.getString("number");

                        if (user.getEmail().equalsIgnoreCase(existingEmail)) {
                            return "EMAIL_EXISTS";
                        }

                        if (user.getNumber().equals(existingNumber)) {
                            return "MOBILE_EXISTS";
                        }
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

            // MySQL duplicate-key protection
            if (e.getErrorCode() == 1062) {

                String message = e.getMessage();

                if (message != null &&
                        message.toLowerCase().contains("email")) {
                    return "EMAIL_EXISTS";
                }

                if (message != null &&
                        message.toLowerCase().contains("number")) {
                    return "MOBILE_EXISTS";
                }

                return "DUPLICATE";
            }

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

        try (Connection connection =
                     DbConnectionHelper.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, mobileNumber);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    String storedPin =
                            resultSet.getString("pin");

                    if (storedPin != null &&
                            storedPin.equals(pin)) {

                        return new User(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("number"),
                                resultSet.getString("email"),
                                storedPin,
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

        try (Connection connection =
                     DbConnectionHelper.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

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


    // CHANGE MPIN
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

        try (Connection connection =
                     DbConnectionHelper.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, newMpin);
            statement.setInt(2, userId);
            statement.setString(3, identifier);
            statement.setString(4, identifier);

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to change MPIN.",
                    e
            );
        }
    }


    // GET ALL USERS
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = """
                SELECT id, name, number, email, pin, balance, role
                FROM users
                ORDER BY id ASC
                """;

        try (Connection connection =
                     DbConnectionHelper.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("number"),
                        resultSet.getString("email"),
                        resultSet.getString("pin"),
                        resultSet.getDouble("balance"),
                        resultSet.getString("role")
                );

                users.add(user);
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to retrieve users.",
                    e
            );
        }

        return users;
    }
}