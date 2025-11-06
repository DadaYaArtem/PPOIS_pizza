
package org.pizzeria.exception;

/**
 * Исключение когда адрес не входит в зону доставки
 */
public class DeliveryZoneNotSupportedException extends DeliveryException {
    
    private final String address;
    
    public DeliveryZoneNotSupportedException(String address) {
        super("Delivery not available for address: " + address);
        this.address = address;
    }
    
    public String getAddress() {
        return address;
    }
}