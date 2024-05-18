package com.fsoteam.eshop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cart {
    private String cartId;
    private Map<String, OrderItem> cartItems;
    private float cartTotal;

    public Cart() {
        this.cartId = UUID.randomUUID().toString();
        this.cartItems = new HashMap<String, OrderItem>();
        this.cartTotal = 0.0f;
    }
    public Cart(String cartId, Map<String, OrderItem> cartItems, float cartTotal) {
        this.cartId = cartId;
        this.cartItems = cartItems;
        this.cartTotal = cartTotal;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Map<String, OrderItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<String, OrderItem> cartItems) {
        this.cartItems = cartItems;
    }

    public float getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(float cartTotal) {
        this.cartTotal = cartTotal;
    }
}