package com.bankApp.bankingApp.util;

public class ValidationHelper {

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidMobileNumber(String number) {
        return number != null &&
                number.matches("09\\d{9}");
    }

    public static boolean isValidPin(String pin) {
        return pin != null &&
                pin.matches("\\d{4}");
    }
}