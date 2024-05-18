package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fsoteam.eshop.model.OrderItem;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.model.Wishlist;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductDetailsViewModel extends ViewModel {
    private DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference(DbCollections.PRODUCTS);
    private String userId = FirebaseAuth.getInstance().getUid();
    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(DbCollections.USERS).child(userId);
    private MediatorLiveData<Product> product = new MediatorLiveData<>();
    private MutableLiveData<Wishlist> userWishlist = new MutableLiveData<>();
    private MutableLiveData<List<OrderItem>> cartItems = new MutableLiveData<>();
    private MutableLiveData<List<Product>> recomProducts = new MutableLiveData<>();
    private MutableLiveData<List<Product>> recomProductsLiveData = new MutableLiveData<>();

    public MediatorLiveData<Product> getProduct() {
        return product;
    }

    public MutableLiveData<Wishlist> getUserWishlist() {
        return userWishlist;
    }

    public MutableLiveData<List<OrderItem>> getCartItems() {
        return cartItems;
    }

    public MutableLiveData<List<Product>> getRecomProductsLiveData() {
        return recomProductsLiveData;
    }

    public void fetchProductData(String pid) {
        productsRef.child(pid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product p = dataSnapshot.getValue(Product.class);
                product.setValue(p);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void fetchUserWishlist() {
        userRef.child("userWishlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Wishlist wishlist = dataSnapshot.getValue(Wishlist.class);
                userWishlist.setValue(wishlist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void fetchUserCartData() {
        userRef.child("userCart").child("cartItems").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<OrderItem> items = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OrderItem orderItem = snapshot.getValue(OrderItem.class);
                    items.add(orderItem);
                }
                cartItems.setValue(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void fetchRecomProducts() {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> products = new ArrayList<>();
                int counter = 1;
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    if(counter > 10) break;
                    Product p = productSnapshot.getValue(Product.class);
                    if (p != null && product.getValue() != null && !p.getProductId().equals(product.getValue().getProductId()) && p.getProductBrand().equals(product.getValue().getProductBrand())) {
                        products.add(p);
                        counter ++;
                    }
                }
                recomProductsLiveData.setValue(products); // Update this line
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void addToBag(Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setItemId(product.getProductId());
        userRef.child("userCart").child("cartItems").child(orderItem.getItemId()).setValue(orderItem);
    }
    public void updateProduct(Product product) {
        productsRef.child(product.getProductId()).setValue(product);
    }

    public void addToWishlist(Product product) {
        userRef.child("userWishlist").child("wishlistProducts").child(product.getProductId()).setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Objects.requireNonNull(userWishlist.getValue()).addProduct(product);
                        // Update the likes count in the database
                        productsRef.child(product.getProductId()).child("likesCount").setValue(product.getLikesCount());
                    }
                });
    }

    public void removeFromWishlist(Product product) {
        userRef.child("userWishlist").child("wishlistProducts").child(product.getProductId()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Objects.requireNonNull(userWishlist.getValue()).removeProduct(product);
                        // Update the likes count in the database
                        productsRef.child(product.getProductId()).child("likesCount").setValue(product.getLikesCount());
                    }
                });
    }
}