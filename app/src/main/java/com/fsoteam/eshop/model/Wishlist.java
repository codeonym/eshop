package com.fsoteam.eshop.model;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {
    private String wishlistId;
    private List<Product> wishlistProducts;
    public Wishlist() {
        this.wishlistId = "";
        this.wishlistProducts = new ArrayList<>();
    }

    public Wishlist(String wishlistId, List<Product> wishlistProducts) {
        this.wishlistId = wishlistId;
        this.wishlistProducts = wishlistProducts;
    }

    public String getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(String wishlistId) {
        this.wishlistId = wishlistId;
    }

    public List<Product> getWishlistProducts() {
        return wishlistProducts;
    }

    public boolean isWishlistEmpty() {
        return wishlistProducts.isEmpty();
    }
    public boolean addProduct(Product product) {
        return this.wishlistProducts.add(product);
    }
    public boolean removeProduct(Product product) {
        return this.wishlistProducts.remove(product);
    }
    public void setWishlistProducts(List<Product> wishlistProducts) {
        this.wishlistProducts = wishlistProducts;
    }
}