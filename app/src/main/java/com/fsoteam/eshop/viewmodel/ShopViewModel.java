package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fsoteam.eshop.model.Category;
import com.fsoteam.eshop.model.Offer;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ShopViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Category>> categoriesLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Offer>> coverOffersLiveData = new MutableLiveData<>();

    private DatabaseReference offersRef = FirebaseDatabase.getInstance().getReference(DbCollections.OFFERS);
    private DatabaseReference categoriesRef = FirebaseDatabase.getInstance().getReference(DbCollections.CATEGORIES);

    public MutableLiveData<ArrayList<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public MutableLiveData<ArrayList<Offer>> getCoverOffersLiveData() {
        return coverOffersLiveData;
    }

    public void loadCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot categorySnapshot: dataSnapshot.getChildren()) {
                    Category category = categorySnapshot.getValue(Category.class);
                    categories.add(category);
                }
                categoriesLiveData.setValue(categories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
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
}