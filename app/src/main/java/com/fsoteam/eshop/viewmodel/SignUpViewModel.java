package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fsoteam.eshop.model.User;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpViewModel extends ViewModel {
    private MutableLiveData<String> resultLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getResultLiveData() {
        return resultLiveData;
    }

    public void signUpUser(String email, String password, String fullName) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = new User();
                        user.setUserName(fullName);
                        user.setUserEmail(email);
                        user.setUserUid(firebaseAuth.getUid());

                        db.child(DbCollections.USERS).child(firebaseAuth.getUid())
                                .setValue(user)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        resultLiveData.setValue("Data Saved");
                                    } else {
                                        resultLiveData.setValue("Data not Saved");
                                    }
                                });
                    } else {
                        resultLiveData.setValue("failed to Authenticate !");
                    }
                });
    }
}