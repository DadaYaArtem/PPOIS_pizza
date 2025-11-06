package org.pizzeria.exception;

/**
 * Исключение при использовании невалидной карты
 */
public class InvalidCardException extends PaymentException {
    
    public InvalidCardException(String message) {
        super(message);
    }
    
    public InvalidCardException(String cardNumber, String reason) {
        super(String.format("Invalid card %s: %s", maskCardNumber(cardNumber), reason));
    }
    
    private static String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "****" + cardNumber.substring(cardNumber.length() - 4);
    }
}