package com.ppois.pizzeria.exception;

/**
 * Исключение при недостаточном количестве средств
 */
public class InsufficientFundsException extends PaymentException {
    
    private final String accountId;
    
    public InsufficientFundsException(String accountId) {
        super("Insufficient funds on account: " + accountId);
        this.accountId = accountId;
    }
    
    public InsufficientFundsException(String message, String accountId) {
        super(message);
        this.accountId = accountId;
    }
    
    public String getAccountId() {
        return accountId;
    }
}