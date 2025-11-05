package com.ppois.pizzeria.domain.common;

import java.util.Objects;

/**
 * Value Object для представления номера телефона.
 * Immutable класс с простой валидацией.
 */
public class PhoneNumber {
    private final String number;

    public PhoneNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        // Простая очистка - убираем пробелы и дефисы
        String cleaned = number.replaceAll("[\\s-]", "");

        // Простая проверка - должны быть только цифры и возможно + в начале
        if (cleaned.length() < 10 || cleaned.length() > 15) {
            throw new IllegalArgumentException("Phone number must be between 10 and 15 digits");
        }

        this.number = cleaned;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return number;
    }
}