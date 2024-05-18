package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fsoteam.eshop.model.User;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private String userUid = FirebaseAuth.getInstance().getUid();
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void loadUserData() {
        db.child(DbCollections.USERS).child(userUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        userLiveData.setValue(user);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle error
                    }
                });
    }

    public Task<Void> updateUserImage(String uri) {
        return db.child(DbCollections.USERS).child(userUid).child("userImage")
                .setValue(uri);
    }
}