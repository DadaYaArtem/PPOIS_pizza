package org.pizzeria.domain.delivery;

import org.pizzeria.domain.common.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Маршрут доставки.
 * Может содержать несколько точек доставки (для оптимизации маршрута курьера).
 */
public class DeliveryRoute {
    private final String id;
    private final Location startLocation; // начальная точка (ресторан)
    private final List<Delivery> deliveries;
    private Location currentLocation;
    private int totalDistance; // в метрах
    private int estimatedTotalTime; // в минутах
    private boolean isOptimized;

    public DeliveryRoute(Location startLocation) {
        if (startLocation == null) {
            throw new IllegalArgumentException("Start location cannot be null");
        }

        this.id = UUID.randomUUID().toString();
        this.startLocation = startLocation;
        this.currentLocation = startLocation;
        this.deliveries = new ArrayList<>();
        this.totalDistance = 0;
        this.estimatedTotalTime = 0;
        this.isOptimized = false;
    }

    public DeliveryRoute(String id, Location startLocation) {
        this.id = id;
        this.startLocation = startLocation;
        this.currentLocation = startLocation;
        this.deliveries = new ArrayList<>();
        this.totalDistance = 0;
        this.estimatedTotalTime = 0;
        this.isOptimized = false;
    }

    public String getId() {
        return id;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        if (currentLocation == null) {
            throw new IllegalArgumentException("Current location cannot be null");
        }
        this.currentLocation = currentLocation;
    }

    public List<Delivery> getDeliveries() {
        return Collections.unmodifiableList(deliveries);
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        if (totalDistance < 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }
        this.totalDistance = totalDistance;
    }

    public int getEstimatedTotalTime() {
        return estimatedTotalTime;
    }

    public void setEstimatedTotalTime(int estimatedTotalTime) {
        if (estimatedTotalTime < 0) {
            throw new IllegalArgumentException("Time cannot be negative");
        }
        this.estimatedTotalTime = estimatedTotalTime;
    }

    public boolean isOptimized() {
        return isOptimized;
    }

    public void setOptimized(boolean optimized) {
        this.isOptimized = optimized;
    }

    /**
     * Добавляет доставку в маршрут
     */
    public void addDelivery(Delivery delivery) {
        if (delivery == null) {
            throw new IllegalArgumentException("Delivery cannot be null");
        }
        if (deliveries.contains(delivery)) {
            throw new IllegalStateException("Delivery already in route");
        }

        deliveries.add(delivery);
        isOptimized = false; // маршрут нужно пересчитать
    }

    /**
     * Удаляет доставку из маршрута
     */
    public void removeDelivery(Delivery delivery) {
        deliveries.remove(delivery);
        isOptimized = false;
    }

    /**
     * Очищает все доставки
     */
    public void clearDeliveries() {
        deliveries.clear();
        totalDistance = 0;
        estimatedTotalTime = 0;
        isOptimized = false;
    }

    /**
     * Проверяет, пустой ли маршрут
     */
    public boolean isEmpty() {
        return deliveries.isEmpty();
    }

    /**
     * Возвращает количество доставок в маршруте
     */
    public int getDeliveryCount() {
        return deliveries.size();
    }

    /**
     * Получает следующую доставку (первую незавершенную)
     */
    public Delivery getNextDelivery() {
        return deliveries.stream()
            .filter(d -> !d.isCompleted())
            .findFirst()
            .orElse(null);
    }

    /**
     * Проверяет, завершены ли все доставки
     */
    public boolean isCompleted() {
        return !deliveries.isEmpty() &&
               deliveries.stream().allMatch(Delivery::isCompleted);
    }

    /**
     * Возвращает количество завершенных доставок
     */
    public int getCompletedCount() {
        return (int) deliveries.stream()
            .filter(Delivery::isCompleted)
            .count();
    }

    /**
     * Рассчитывает прогресс маршрута (0.0 - 1.0)
     */
    public double getProgress() {
        if (deliveries.isEmpty()) {
            return 0.0;
        }
        return (double) getCompletedCount() / deliveries.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryRoute that = (DeliveryRoute) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("DeliveryRoute{id='%s', deliveries=%d, completed=%d/%d, distance=%dm, time=%dmin}",
            id, deliveries.size(), getCompletedCount(), deliveries.size(), totalDistance, estimatedTotalTime);
    }
}
