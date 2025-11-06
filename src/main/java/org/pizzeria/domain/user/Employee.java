package org.pizzeria.domain.user;

import org.pizzeria.domain.common.Email;
import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.common.PhoneNumber;

import java.time.LocalDate;

/**
 * Абстрактный класс для всех сотрудников пиццерии.
 * Общие поля: зарплата, дата найма, статус занятости.
 */
public abstract class Employee extends User {
    protected Money salary;
    protected LocalDate hireDate;
    protected boolean isAvailable;

    protected Employee(String name, Email email, PhoneNumber phoneNumber, 
                      String passwordHash, Money salary) {
        super(name, email, phoneNumber, passwordHash);
        if (salary == null) {
            throw new IllegalArgumentException("Salary cannot be null");
        }
        this.salary = salary;
        this.hireDate = LocalDate.now();
        this.isAvailable = true;
    }

    protected Employee(String id, String name, Email email, PhoneNumber phoneNumber,
                      String passwordHash, Money salary) {
        super(id, name, email, phoneNumber, passwordHash);
        this.salary = salary;
        this.hireDate = LocalDate.now();
        this.isAvailable = true;
    }

    public Money getSalary() {
        return salary;
    }

    public void setSalary(Money salary) {
        if (salary == null) {
            throw new IllegalArgumentException("Salary cannot be null");
        }
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    /**
     * Вычисляет стаж работы в годах
     */
    public int getYearsOfService() {
        return LocalDate.now().getYear() - hireDate.getYear();
    }
}