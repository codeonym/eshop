package com.fsoteam.eshop.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.model.User;
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

public class FavViewModel extends ViewModel {
    private MutableLiveData<List<Product>> favProductsLiveData = new MutableLiveData<>();

    private String currentUserId = FirebaseAuth.getInstance().getUid();
    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(DbCollections.USERS).child(currentUserId);
    private DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference(DbCollections.PRODUCTS);

    public MutableLiveData<List<Product>> getFavProductsLiveData() {
        return favProductsLiveData;
    }

    public void loadFavProducts() {
        userRef.child("userWishlist").child("wishlistProducts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> tmp = new ArrayList<>();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    tmp.add(product);
                }
                favProductsLiveData.setValue(tmp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
    public void removeProductFromWishlist(Product product) {
        userRef.child("userWishlist").child("wishlistProducts").child(product.getProductId()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        productsRef.child(product.getProductId()).child("likesCount").setValue(product.getLikesCount() - 1);
                    }
                });
    }
}