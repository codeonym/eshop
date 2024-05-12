package com.fsoteam.eshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wishlist {
    private String wishlistId;
    private List<Product> wishlistProducts;
    public Wishlist() {
        this.wishlistId = UUID.randomUUID().toString();
        this.wishlistProducts = new ArrayList<Product>();
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

    public boolean containsProduct(Product product) {
        for(Product p: wishlistProducts){
            if(product.getProductId() != null && product.getProductId().equals(p.getProductId())){
                return true;
            }
        }
        return false;
    }

    public boolean addProduct(Product product) {
        return this.wishlistProducts.add(product);
    }
    public boolean removeProduct(Product product) {
        return this.wishlistProducts.remove(product);
    }
    public boolean removeProductById(String productId) {
        for(Product p: wishlistProducts){
            if(productId != null && productId.equals(p.getProductId())){
                return wishlistProducts.remove(p);
            }
        }
        return false;
    }
    public void setWishlistProducts(List<Product> wishlistProducts) {
        this.wishlistProducts = wishlistProducts;
    }
}