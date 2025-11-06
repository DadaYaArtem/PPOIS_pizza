package org.pizzeria.service;

import org.pizzeria.domain.common.Address;
import org.pizzeria.domain.common.DeliveryStatus;
import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.delivery.Delivery;
import org.pizzeria.domain.delivery.DeliveryRoute;
import org.pizzeria.domain.order.Order;
import org.pizzeria.domain.user.Courier;
import org.pizzeria.validator.DeliveryValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления доставкой.
 * Координирует создание и назначение доставок курьерам.
 */
public class DeliveryService {
    private final List<Delivery> deliveries;
    private final List<DeliveryRoute> routes;
    private final Money standardDeliveryFee;

    public DeliveryService(Money standardDeliveryFee) {
        this.deliveries = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.standardDeliveryFee = standardDeliveryFee;
    }

    public DeliveryService() {
        this(Money.of(5.00)); // $5 по умолчанию
    }

    /**
     * Создает доставку для заказа
     */
    public Delivery createDelivery(Order order, Address address) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }

        Delivery delivery = new Delivery(order, address, standardDeliveryFee);
        deliveries.add(delivery);

        return delivery;
    }

    /**
     * Создает доставку с кастомной стоимостью
     */
    public Delivery createDelivery(Order order, Address address, Money deliveryFee) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }

        Delivery delivery = new Delivery(order, address, deliveryFee);
        deliveries.add(delivery);

        return delivery;
    }

    /**
     * Назначает курьера на доставку
     */
    public void assignCourier(Delivery delivery, Courier courier) {
        if (!deliveries.contains(delivery)) {
            throw new IllegalArgumentException("Delivery not found");
        }

        DeliveryValidator.validateAndThrow(delivery);

        delivery.assignCourier(courier);
    }

    /**
     * Обновляет статус доставки
     */
    public void updateDeliveryStatus(Delivery delivery, DeliveryStatus newStatus) {
        if (!deliveries.contains(delivery)) {
            throw new IllegalArgumentException("Delivery not found");
        }

        delivery.setStatus(newStatus);
    }

    /**
     * Отмечает доставку как доставленную
     */
    public void markAsDelivered(Delivery delivery) {
        if (!deliveries.contains(delivery)) {
            throw new IllegalArgumentException("Delivery not found");
        }

        delivery.setStatus(DeliveryStatus.DELIVERED);
    }

    /**
     * Отмечает доставку как неудавшуюся
     */
    public void markAsFailed(Delivery delivery, String reason) {
        if (!deliveries.contains(delivery)) {
            throw new IllegalArgumentException("Delivery not found");
        }

        delivery.setStatus(DeliveryStatus.FAILED);
        delivery.setNotes("Failed: " + reason);
    }

    /**
     * Создает маршрут доставки
     */
    public DeliveryRoute createRoute(org.pizzeria.domain.common.Location startLocation) {
        DeliveryRoute route = new DeliveryRoute(startLocation);
        routes.add(route);
        return route;
    }

    /**
     * Добавляет доставку в маршрут
     */
    public void addDeliveryToRoute(DeliveryRoute route, Delivery delivery) {
        if (!routes.contains(route)) {
            throw new IllegalArgumentException("Route not found");
        }
        if (!deliveries.contains(delivery)) {
            throw new IllegalArgumentException("Delivery not found");
        }

        route.addDelivery(delivery);
    }

    /**
     * Находит доставку по ID
     */
    public Optional<Delivery> findDeliveryById(String deliveryId) {
        return deliveries.stream()
            .filter(d -> d.getId().equals(deliveryId))
            .findFirst();
    }

    /**
     * Находит доставку для заказа
     */
    public Optional<Delivery> findDeliveryByOrder(Order order) {
        return deliveries.stream()
            .filter(d -> d.getOrder().equals(order))
            .findFirst();
    }

    /**
     * Находит доставки по трекинг-коду
     */
    public Optional<Delivery> findDeliveryByTrackingCode(String trackingCode) {
        return deliveries.stream()
            .filter(d -> d.getTrackingCode().equals(trackingCode))
            .findFirst();
    }

    /**
     * Находит доставки курьера
     */
    public List<Delivery> findDeliveriesByCourier(Courier courier) {
        return deliveries.stream()
            .filter(d -> d.getCourier() != null && d.getCourier().equals(courier))
            .toList();
    }

    /**
     * Находит доставки по статусу
     */
    public List<Delivery> findDeliveriesByStatus(DeliveryStatus status) {
        return deliveries.stream()
            .filter(d -> d.getStatus() == status)
            .toList();
    }

    /**
     * Находит доставки в ожидании назначения
     */
    public List<Delivery> findPendingDeliveries() {
        return findDeliveriesByStatus(DeliveryStatus.PENDING);
    }

    /**
     * Возвращает все доставки
     */
    public List<Delivery> getAllDeliveries() {
        return new ArrayList<>(deliveries);
    }

    /**
     * Возвращает все маршруты
     */
    public List<DeliveryRoute> getAllRoutes() {
        return new ArrayList<>(routes);
    }

    /**
     * Удаляет доставку (только для тестов)
     */
    void removeDelivery(Delivery delivery) {
        deliveries.remove(delivery);
    }
}
