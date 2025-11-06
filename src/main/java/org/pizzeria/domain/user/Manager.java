package org.pizzeria.domain.user;

import org.pizzeria.domain.common.Email;
import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.common.PhoneNumber;

/**
 * Менеджер пиццерии.
 * Имеет расширенные права: управление меню, персоналом, просмотр отчетов.
 */
public class Manager extends Employee {
    private String department; // "Kitchen", "Delivery", "General"
    private boolean canManageMenu;
    private boolean canManageEmployees;

    public Manager(String name, Email email, PhoneNumber phoneNumber,
                   String passwordHash, Money salary, String department) {
        super(name, email, phoneNumber, passwordHash, salary);
        this.department = department != null ? department : "General";
        this.canManageMenu = true;
        this.canManageEmployees = true;
    }

    public Manager(String id, String name, Email email, PhoneNumber phoneNumber,
                   String passwordHash, Money salary, String department) {
        super(id, name, email, phoneNumber, passwordHash, salary);
        this.department = department;
        this.canManageMenu = true;
        this.canManageEmployees = true;
    }

    @Override
    public String getRole() {
        return "MANAGER";
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean canManageMenu() {
        return canManageMenu;
    }

    public void setCanManageMenu(boolean canManageMenu) {
        this.canManageMenu = canManageMenu;
    }

    public boolean canManageEmployees() {
        return canManageEmployees;
    }

    public void setCanManageEmployees(boolean canManageEmployees) {
        this.canManageEmployees = canManageEmployees;
    }

    /**
     * Менеджер всегда "доступен" (не имеет ограничений как Cook или Courier)
     */
    @Override
    public boolean isAvailable() {
        return super.isAvailable();
    }
}