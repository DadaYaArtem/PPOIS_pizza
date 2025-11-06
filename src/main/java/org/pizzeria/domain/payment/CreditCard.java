package org.pizzeria.domain.payment;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Кредитная/дебетовая карта как способ оплаты.
 * Value Object с валидацией данных карты.
 */
public class CreditCard implements PaymentMethod {
    private final String cardNumber;
    private final String cardholderName;
    private final YearMonth expiryDate;
    private final String cvv;
    private final String cardType; // VISA, MasterCard, etc.

    public CreditCard(String cardNumber, String cardholderName,
                     YearMonth expiryDate, String cvv) {
        if (cardNumber == null || !isValidCardNumber(cardNumber)) {
            throw new IllegalArgumentException("Invalid card number");
        }
        if (cardholderName == null || cardholderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Cardholder name cannot be empty");
        }
        if (expiryDate == null || isExpired(expiryDate)) {
            throw new IllegalArgumentException("Card is expired");
        }
        if (cvv == null || !isValidCVV(cvv)) {
            throw new IllegalArgumentException("Invalid CVV");
        }

        this.cardNumber = cardNumber.replaceAll("\\s", "");
        this.cardholderName = cardholderName.trim().toUpperCase();
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardType = detectCardType(this.cardNumber);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public YearMonth getExpiryDate() {
        return expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public String getCardType() {
        return cardType;
    }

    @Override
    public String getType() {
        return "CREDIT_CARD";
    }

    @Override
    public String getDescription() {
        return String.format("%s ending in %s", cardType, getLastFourDigits());
    }

    @Override
    public boolean isValid() {
        return !isExpired(expiryDate) &&
               isValidCardNumber(cardNumber) &&
               isValidCVV(cvv);
    }

    @Override
    public String getMaskedInfo() {
        return String.format("%s **** **** **** %s (%s)",
            cardType, getLastFourDigits(), cardholderName);
    }

    /**
     * Возвращает последние 4 цифры карты
     */
    public String getLastFourDigits() {
        if (cardNumber.length() >= 4) {
            return cardNumber.substring(cardNumber.length() - 4);
        }
        return cardNumber;
    }

    /**
     * Проверяет, истек ли срок действия карты
     */
    private boolean isExpired(YearMonth expiryDate) {
        YearMonth now = YearMonth.now();
        return expiryDate.isBefore(now);
    }

    /**
     * Базовая валидация номера карты (проверка длины и что это цифры)
     */
    private boolean isValidCardNumber(String number) {
        if (number == null) {
            return false;
        }
        String cleaned = number.replaceAll("\\s", "");
        return cleaned.matches("\\d{13,19}") && luhnCheck(cleaned);
    }

    /**
     * Алгоритм Луна для проверки номера карты
     */
    private boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }

    /**
     * Валидация CVV (3 или 4 цифры)
     */
    private boolean isValidCVV(String cvv) {
        return cvv != null && cvv.matches("\\d{3,4}");
    }

    /**
     * Определяет тип карты по номеру
     */
    private String detectCardType(String number) {
        if (number.startsWith("4")) {
            return "VISA";
        } else if (number.matches("^5[1-5].*")) {
            return "MasterCard";
        } else if (number.matches("^3[47].*")) {
            return "American Express";
        } else if (number.startsWith("6")) {
            return "Discover";
        }
        return "Unknown";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(cardNumber, that.cardNumber) &&
               Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, expiryDate);
    }

    @Override
    public String toString() {
        return getMaskedInfo();
    }
}
