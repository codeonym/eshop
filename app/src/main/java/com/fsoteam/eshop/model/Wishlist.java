package com.fsoteam.eshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Wishlist {
    private String wishlistId;
    private Map<String, Product> wishlistProducts;
    public Wishlist() {
        this.wishlistId = UUID.randomUUID().toString();
        this.wishlistProducts = new HashMap<String, Product>();
    }

    public Wishlist(String wishlistId, Map<String, Product> wishlistProducts) {
        this.wishlistId = wishlistId;
        this.wishlistProducts = wishlistProducts;
    }

    public String getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(String wishlistId) {
        this.wishlistId = wishlistId;
    }

    public Map<String, Product> getWishlistProducts() {
        return wishlistProducts;
    }

    public Map<String, Product> getWishlistProductsMap() {
        return wishlistProducts;
    }

    public boolean containsProduct(Product product) {
        return wishlistProducts.containsKey(product.getProductId());
    }

    public void addProduct(Product product) {
        this.wishlistProducts.put(product.getProductId(), product);
    }
    public void removeProduct(Product product) {
        this.wishlistProducts.remove(product.getProductId());
    }
    public void removeProductById(String productId) {
        wishlistProducts.remove(productId);
    }
    public void setWishlistProductsByList(List<Product> products) {

        this.wishlistProducts.clear();
        Map<String, Product> tmpWishlistProducts= new HashMap<String, Product>();
        for(Product p: products){
            if(p.getProductId() != null)
                tmpWishlistProducts.put(p.getProductId(), p);
        }
        this.wishlistProducts.putAll(tmpWishlistProducts);
    }
    public void setWishlistProducts(Map<String, Product> wishlistProducts) {
        this.wishlistProducts = wishlistProducts;
    }

}