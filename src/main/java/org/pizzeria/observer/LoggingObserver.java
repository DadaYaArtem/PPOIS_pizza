package org.pizzeria.observer;

import org.pizzeria.domain.order.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Наблюдатель для логирования событий заказов.
 * Конкретная реализация OrderObserver.
 */
public class LoggingObserver implements OrderObserver {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String loggerName;

    public LoggingObserver(String loggerName) {
        this.loggerName = loggerName;
    }

    public LoggingObserver() {
        this("OrderLogger");
    }

    @Override
    public void onOrderStatusChanged(Order order) {
        log("ORDER_STATUS_CHANGED",
            String.format("Order %s changed status to %s",
                order.getId(), order.getStatus()));
    }

    @Override
    public void onOrderCreated(Order order) {
        log("ORDER_CREATED",
            String.format("New order created: %s, Customer: %s, Items: %d, Total: %s",
                order.getId(), order.getCustomer().getName(),
                order.getItemCount(), order.getTotal()));
    }

    @Override
    public void onOrderCancelled(Order order) {
        log("ORDER_CANCELLED",
            String.format("Order %s cancelled by customer %s",
                order.getId(), order.getCustomer().getName()));
    }

    @Override
    public void onOrderCompleted(Order order) {
        log("ORDER_COMPLETED",
            String.format("Order %s completed. Customer: %s, Total: %s",
                order.getId(), order.getCustomer().getName(), order.getTotal()));
    }

    /**
     * Логирует событие
     */
    private void log(String eventType, String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.out.printf("[%s] [%s] %s: %s%n",
            timestamp, loggerName, eventType, message);
    }

    @Override
    public String toString() {
        return "LoggingObserver{logger='" + loggerName + "'}";
    }
}
