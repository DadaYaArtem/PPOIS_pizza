package com.ppois.pizzeria.domain.user;

import com.ppois.pizzeria.domain.common.Email;
import com.ppois.pizzeria.domain.common.PhoneNumber;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Абстрактный базовый класс для всех пользователей системы.
 * Применяем принцип DRY и наследование.
 */
public abstract class User {
    protected final String id;
    protected String name;
    protected Email email;
    protected PhoneNumber phoneNumber;
    protected String passwordHash;
    protected final LocalDateTime createdAt;
    protected boolean isActive;

    protected User(String name, Email email, PhoneNumber phoneNumber, String passwordHash) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (phoneNumber == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }
        
        this.id = UUID.randomUUID().toString();
        this.name = name.trim();
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }

    // Для тестирования - конструктор с заданным ID
    protected User(String id, String name, Email email, PhoneNumber phoneNumber, String passwordHash) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name.trim();
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        this.email = email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumber == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    // Абстрактный метод - каждый тип пользователя определяет свою роль
    public abstract String getRole();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s{id='%s', name='%s', email='%s'}", 
            getRole(), id, name, email.getAddress());
    }
}