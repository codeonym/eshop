package com.fsoteam.eshop.model;

import java.util.UUID;

public class OrderItem {

    private String itemId;

    private int quantity;
    private Product product;

    public OrderItem() {
        this.itemId = UUID.randomUUID().toString();
        this.product = new Product();
        this.quantity = 1;
    }

    public OrderItem(String itemId, int quantity, Product product) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.product = product;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
