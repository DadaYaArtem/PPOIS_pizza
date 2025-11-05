package com.ppois.pizzeria.domain.common;

import java.util.Objects;

/**
 * Value Object для представления email адреса.
 * Immutable класс с простой валидацией.
 */
public class Email {
    private final String address;

    public Email(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be empty");
        }

        String trimmed = address.trim().toLowerCase();

        // Простая проверка наличия @ и точки после @
        if (!trimmed.contains("@") || !trimmed.substring(trimmed.indexOf("@")).contains(".")) {
            throw new IllegalArgumentException("Invalid email format: " + address);
        }

        this.address = trimmed;
    }

    public String getAddress() {
        return address;
    }

    public String getDomain() {
        return address.substring(address.indexOf('@') + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return address;
    }
}