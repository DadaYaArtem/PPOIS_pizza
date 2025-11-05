package com.ppois.pizzeria.domain.common;

import java.util.Objects;

/**
 * Value Object для представления адреса.
 * Immutable класс.
 */
public class Address {
    private final String street;
    private final String building;
    private final String apartment;
    private final String city;
    private final String postalCode;
    private final String additionalInfo; // подъезд, этаж, домофон

    public Address(String street, String building, String apartment, 
                   String city, String postalCode, String additionalInfo) {
        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("Street cannot be empty");
        }
        if (building == null || building.trim().isEmpty()) {
            throw new IllegalArgumentException("Building cannot be empty");
        }
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be empty");
        }
        
        this.street = street.trim();
        this.building = building.trim();
        this.apartment = apartment != null ? apartment.trim() : "";
        this.city = city.trim();
        this.postalCode = postalCode != null ? postalCode.trim() : "";
        this.additionalInfo = additionalInfo != null ? additionalInfo.trim() : "";
    }

    public Address(String street, String building, String city) {
        this(street, building, null, city, null, null);
    }

    public String getStreet() {
        return street;
    }

    public String getBuilding() {
        return building;
    }

    public String getApartment() {
        return apartment;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(city).append(", ");
        sb.append(street).append(", ");
        sb.append(building);
        
        if (!apartment.isEmpty()) {
            sb.append(", apt. ").append(apartment);
        }
        
        if (!postalCode.isEmpty()) {
            sb.append(", ").append(postalCode);
        }
        
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
               Objects.equals(building, address.building) &&
               Objects.equals(apartment, address.apartment) &&
               Objects.equals(city, address.city) &&
               Objects.equals(postalCode, address.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, building, apartment, city, postalCode);
    }

    @Override
    public String toString() {
        return getFullAddress();
    }
}