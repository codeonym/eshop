package com.fsoteam.eshop.model;

import android.os.Build;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String orderId;
    private List<Product> orderProducts;
    private float orderTotalAmount;
    private String orderStatus;
    private LocalDate orderDate;
    public Order() {
        this.orderId = "";
        this.orderProducts = new ArrayList<>();
        this.orderTotalAmount = 0.0f;
        this.orderStatus = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.orderDate = LocalDate.now();
        }
    }

    public Order(String orderId, List<Product> orderProducts, float orderTotalAmount,LocalDate orderDate, String orderStatus) {
        this.orderId = orderId;
        this.orderProducts = orderProducts;
        this.orderTotalAmount = orderTotalAmount;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public List<Product> getOrderProducts() {
        return orderProducts;
    }
    public boolean isOrderEmpty() {
        return orderProducts.isEmpty();
    }

    public boolean addProduct(Product product) {
        return orderProducts.add(product);
    }
    public boolean removeProduct(Product product) {
        return orderProducts.remove(product);
    }
    public void setOrderProducts(List<Product> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public float getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(float orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderSize() {
        return orderProducts.size();
    }
}