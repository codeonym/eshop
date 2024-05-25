package com.fsoteam.eshop.utils;

public enum OrderStatus {
    PENDING,      // Order has been placed but not yet accepted/processed
    PROCESSING,   // Order is being processed
    PACKAGED,     // Order has been packaged and is ready for shipping
    SHIPPED,      // Order has been shipped
    ON_HOLD,      // Order is on hold
    DELIVERED,    // Order has been delivered
    CONFIRMED,    // Order has been confirmed by the customer
    COMPLETED,    // Order has been completed
    REFUND_REQUESTED, // Refund has been requested by the customer
    RETURNED,     // Order has been returned by the customer
    REFUNDED,     // Refund has been issued for the returned order
    CANCELLED,    // Order has been cancelled
    REJECTED      // Order has been rejected
}