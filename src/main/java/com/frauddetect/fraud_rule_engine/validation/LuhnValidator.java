package com.frauddetect.fraud_rule_engine.validation;

public final class LuhnValidator {
    private LuhnValidator(){

    }
    public static boolean isValid(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 12 || cardNumber.length() > 19) {
            return false;
        }
        int sum = 0;
        boolean doubleThisDigit = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            char c = cardNumber.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
            int digit = c - '0';
            if (doubleThisDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            doubleThisDigit = !doubleThisDigit;
        }
        return sum % 10 == 0;
    }
}
