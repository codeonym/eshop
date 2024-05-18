package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProductViewModel extends ViewModel {
    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(DbCollections.USERS);
    private DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference(DbCollections.PRODUCTS);
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public void addProductToWishlist(Product product) {
        userRef.child(currentUserId).child("userWishlist").child("wishlistProducts").child(product.getProductId()).setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        // Update the likes count in the database
                        productsRef.child(product.getProductId()).child("likesCount").setValue(product.getLikesCount() + 1);
                    }
                });
    }

    public void removeProductFromWishlist(Product product) {
        userRef.child(currentUserId).child("userWishlist").child("wishlistProducts").child(product.getProductId()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Update the likes count in the database
                        productsRef.child(product.getProductId()).child("likesCount").setValue(product.getLikesCount() - 1);
                    }
                });
    }
    public LiveData<User> getCurrentUser() {
        MutableLiveData<User> currentUser = new MutableLiveData<>();
        userRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                currentUser.setValue(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
        return currentUser;
    }
}