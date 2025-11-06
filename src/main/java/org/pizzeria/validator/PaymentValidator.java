package org.pizzeria.validator;

import org.pizzeria.domain.payment.Payment;
import org.pizzeria.domain.payment.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Валидатор для платежей.
 * Проверяет корректность данных платежа.
 */
public class PaymentValidator {

    /**
     * Валидирует платеж
     * @return список ошибок (пустой, если платеж валиден)
     */
    public static List<String> validate(Payment payment) {
        List<String> errors = new ArrayList<>();

        if (payment == null) {
            errors.add("Payment cannot be null");
            return errors;
        }

        // Проверяем заказ
        if (payment.getOrder() == null) {
            errors.add("Payment must be associated with an order");
        }

        // Проверяем способ оплаты
        PaymentMethod method = payment.getPaymentMethod();
        if (method == null) {
            errors.add("Payment method cannot be null");
        } else if (!method.isValid()) {
            errors.add("Payment method is not valid");
        }

        // Проверяем сумму
        if (payment.getAmount() == null || payment.getAmount().isZero()) {
            errors.add("Payment amount must be positive");
        }

        // Проверяем соответствие суммы платежа и заказа
        if (payment.getOrder() != null && payment.getAmount() != null) {
            if (!payment.getAmount().equals(payment.getOrder().getTotal())) {
                errors.add("Payment amount must match order total");
            }
        }

        return errors;
    }

    /**
     * Проверяет, валиден ли платеж
     */
    public static boolean isValid(Payment payment) {
        return validate(payment).isEmpty();
    }

    /**
     * Валидирует платеж и выбрасывает исключение при ошибках
     */
    public static void validateAndThrow(Payment payment) {
        List<String> errors = validate(payment);
        if (!errors.isEmpty()) {
            throw new IllegalStateException("Payment validation failed: " +
                String.join(", ", errors));
        }
    }
}
