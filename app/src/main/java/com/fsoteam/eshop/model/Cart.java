package com.fsoteam.eshop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String cartId;
    private List<Product> cartProducts;
    private float cartTotal;

    public Cart() {
        this.cartId = "";
        this.cartProducts = new ArrayList<>();
        this.cartTotal = 0.0f;
    }
    public Cart(String cartId, List<Product> cartProducts, float cartTotal) {
        this.cartId = cartId;
        this.cartProducts = cartProducts;
        this.cartTotal = cartTotal;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public List<Product> getCartProducts() {
        return cartProducts;
    }
    public boolean isCartEmpty() {
        return cartProducts.isEmpty();
    }
    public boolean addProduct(Product product) {
        return this.cartProducts.add(product);
    }

    public boolean removeProduct(Product product) {
        return this.cartProducts.remove(product);
    }
    public void setCartProducts(List<Product> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public float getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(float cartTotal) {
        this.cartTotal = cartTotal;
    }
}