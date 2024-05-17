package com.fsoteam.eshop.model;

import android.os.Build;

import com.fsoteam.eshop.utils.OrderStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Order {
    private String orderId;
    private List<OrderItem> orderProducts;
    private float orderTotalAmount;
    private ShipmentDetails shipmentDetails;
    private OrderStatus orderStatus;
    private long orderDate;
    public Order() {
        this.orderId = UUID.randomUUID().toString();
        this.orderProducts = new ArrayList<OrderItem>();
        this.orderTotalAmount = 0.0f;
        this.shipmentDetails = new ShipmentDetails();
        this.orderStatus = OrderStatus.PENDING;
        this.orderDate = System.currentTimeMillis();
    }

    public Order(String orderId, List<OrderItem> orderProducts,ShipmentDetails shipmentDetails, float orderTotalAmount,long orderDate, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderProducts = orderProducts;
        this.orderTotalAmount = orderTotalAmount;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.shipmentDetails = shipmentDetails;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public List<OrderItem> getOrderProducts() {
        return orderProducts;
    }

    public boolean addProduct(OrderItem orderItem) {
        return orderProducts.add(orderItem);
    }
    public boolean removeProduct(OrderItem orderItem){
        return orderProducts.remove(orderItem);
    }
    public void setOrderProducts(List<OrderItem> orderItems) {
        this.orderProducts = orderItems;
    }

    public float getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(float orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ShipmentDetails getShipmentDetails() {
        return shipmentDetails;
    }

    public void setShipmentDetails(ShipmentDetails shipmentDetails) {
        this.shipmentDetails = shipmentDetails;
    }

    public int getOrderSize() {
        return orderProducts.size();
    }
}