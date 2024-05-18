package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fsoteam.eshop.model.Offer;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Offer>> coverOffersLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> newProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> saleProductsLiveData = new MutableLiveData<>();

    private DatabaseReference offersRef = FirebaseDatabase.getInstance().getReference(DbCollections.OFFERS);
    private DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference(DbCollections.PRODUCTS);

    public MutableLiveData<ArrayList<Offer>> getCoverOffersLiveData() {
        return coverOffersLiveData;
    }

    public MutableLiveData<ArrayList<Product>> getNewProductsLiveData() {
        return newProductsLiveData;
    }

    public MutableLiveData<ArrayList<Product>> getSaleProductsLiveData() {
        return saleProductsLiveData;
    }

    public void loadCoverOffers() {
        ArrayList<Offer> coverOffers = new ArrayList<>();
        offersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot offerSnapshot: dataSnapshot.getChildren()) {
                    Offer offer = offerSnapshot.getValue(Offer.class);
                    coverOffers.add(offer);
                }
                coverOffersLiveData.setValue(coverOffers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public void loadNewProducts() {
        ArrayList<Product> newProducts = new ArrayList<>();
        productsRef.orderByChild("productAddDate").limitToLast(10)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                            Product product = productSnapshot.getValue(Product.class);
                            newProducts.add(product);
                        }

                        Collections.reverse(newProducts);
                        newProductsLiveData.setValue(newProducts);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle error
                    }
                });
    }

    public void loadSaleProducts() {
        ArrayList<Product> saleProducts = new ArrayList<>();
        productsRef.orderByChild("likesCount").limitToLast(10)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                            Product product = productSnapshot.getValue(Product.class);
                            if(product != null)
                                saleProducts.add(product);
                        }

                        Collections.reverse(saleProducts);
                        saleProductsLiveData.setValue(saleProducts);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle error
                    }
                });
    }
}