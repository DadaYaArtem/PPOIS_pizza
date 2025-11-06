package org.pizzeria.observer;

import org.pizzeria.domain.order.Order;

/**
 * Наблюдатель для отправки email уведомлений.
 * Конкретная реализация OrderObserver.
 */
public class EmailNotificationObserver implements OrderObserver {
    private final String emailService;

    public EmailNotificationObserver(String emailService) {
        this.emailService = emailService;
    }

    public EmailNotificationObserver() {
        this("DefaultEmailService");
    }

    @Override
    public void onOrderStatusChanged(Order order) {
        String email = order.getCustomer().getEmail().getAddress();
        String message = String.format(
            "Order #%s status changed to: %s",
            order.getId().substring(0, 8),
            order.getStatus()
        );
        sendEmail(email, "Order Status Update", message);
    }

    @Override
    public void onOrderCreated(Order order) {
        String email = order.getCustomer().getEmail().getAddress();
        String message = String.format(
            "Your order #%s has been created. Total: %s",
            order.getId().substring(0, 8),
            order.getTotal()
        );
        sendEmail(email, "Order Confirmation", message);
    }

    @Override
    public void onOrderCancelled(Order order) {
        String email = order.getCustomer().getEmail().getAddress();
        String message = String.format(
            "Order #%s has been cancelled.",
            order.getId().substring(0, 8)
        );
        sendEmail(email, "Order Cancelled", message);
    }

    @Override
    public void onOrderCompleted(Order order) {
        String email = order.getCustomer().getEmail().getAddress();
        String message = String.format(
            "Order #%s has been completed. Thank you for your order!",
            order.getId().substring(0, 8)
        );
        sendEmail(email, "Order Completed", message);
    }

    /**
     * Имитация отправки email
     */
    private void sendEmail(String to, String subject, String body) {
        // В реальном приложении здесь была бы интеграция с email сервисом
        System.out.printf("[%s] Sending email to %s: %s - %s%n",
            emailService, to, subject, body);
    }

    @Override
    public String toString() {
        return "EmailNotificationObserver{service='" + emailService + "'}";
    }
}
