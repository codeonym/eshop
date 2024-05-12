package com.fsoteam.eshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cart {
    private String cartId;
    private List<OrderItem> cartItems;
    private float cartTotal;

    public Cart() {
        this.cartId = UUID.randomUUID().toString();
        this.cartItems = new ArrayList<OrderItem>();
        this.cartTotal = 0.0f;
    }
    public Cart(String cartId, List<OrderItem> cartItems, float cartTotal) {
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

    public List<OrderItem> getCartItems() {
        return cartItems;
    }
    public boolean addProduct(OrderItem product) {
        return this.cartItems.add(product);
    }

    public boolean removeProduct(Product product) {
        return this.cartItems.remove(product);
    }
    public boolean removeProductById(String productId) {
        for (OrderItem item : this.cartItems) {
            if (item.getProduct().getProductId().equals(productId)) {
                return this.cartItems.remove(item);
            }
        }
        return false;
    }
    public void setCartItems(List<OrderItem> cartItems) {
        this.cartItems = cartItems;
    }

    public float getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(float cartTotal) {
        this.cartTotal = cartTotal;
    }
}