package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fsoteam.eshop.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {

    private MutableLiveData<List<Product>> allProducts;
    private DatabaseReference databaseReference;

    public CartViewModel() {
        allProducts = new MutableLiveData<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("products");
        loadProducts();
    }

    public LiveData<List<Product>> getAllproducts() {
        return allProducts;
    }

    private void loadProducts() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> products = new ArrayList<>();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    products.add(product);
                }
                allProducts.setValue(products);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
}