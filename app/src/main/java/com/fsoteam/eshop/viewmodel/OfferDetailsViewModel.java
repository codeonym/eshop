package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fsoteam.eshop.model.Offer;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OfferDetailsViewModel extends ViewModel {
    private MutableLiveData<Offer> offerLiveData = new MutableLiveData<>();

    public MutableLiveData<Offer> getOfferLiveData() {
        return offerLiveData;
    }

    public void loadOffer(String offerId) {
        DatabaseReference offersRef = FirebaseDatabase.getInstance().getReference(DbCollections.OFFERS).child(offerId);
        offersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Offer offer = dataSnapshot.getValue(Offer.class);
                offerLiveData.setValue(offer);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}