package org.pizzeria.observer;

import org.pizzeria.domain.order.Order;

/**
 * Наблюдатель для отправки SMS уведомлений.
 * Конкретная реализация OrderObserver.
 */
public class SMSNotificationObserver implements OrderObserver {
    private final String smsService;
    private final boolean sendForAllEvents;

    public SMSNotificationObserver(String smsService, boolean sendForAllEvents) {
        this.smsService = smsService;
        this.sendForAllEvents = sendForAllEvents;
    }

    public SMSNotificationObserver() {
        this("DefaultSMSService", false);
    }

    @Override
    public void onOrderStatusChanged(Order order) {
        if (sendForAllEvents) {
            String phone = order.getCustomer().getPhoneNumber().getNumber();
            String message = String.format(
                "Order #%s: %s",
                order.getId().substring(0, 8),
                order.getStatus()
            );
            sendSMS(phone, message);
        }
    }

    @Override
    public void onOrderCreated(Order order) {
        String phone = order.getCustomer().getPhoneNumber().getNumber();
        String message = String.format(
            "Order #%s created. Total: %s",
            order.getId().substring(0, 8),
            order.getTotal()
        );
        sendSMS(phone, message);
    }

    @Override
    public void onOrderCancelled(Order order) {
        String phone = order.getCustomer().getPhoneNumber().getNumber();
        String message = String.format(
            "Order #%s cancelled",
            order.getId().substring(0, 8)
        );
        sendSMS(phone, message);
    }

    @Override
    public void onOrderCompleted(Order order) {
        String phone = order.getCustomer().getPhoneNumber().getNumber();
        String message = String.format(
            "Order #%s completed. Enjoy!",
            order.getId().substring(0, 8)
        );
        sendSMS(phone, message);
    }

    /**
     * Имитация отправки SMS
     */
    private void sendSMS(String phoneNumber, String message) {
        // В реальном приложении здесь была бы интеграция с SMS сервисом
        System.out.printf("[%s] Sending SMS to %s: %s%n",
            smsService, phoneNumber, message);
    }

    @Override
    public String toString() {
        return "SMSNotificationObserver{service='" + smsService + "'}";
    }
}
