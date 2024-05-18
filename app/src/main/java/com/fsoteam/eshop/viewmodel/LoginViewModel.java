package com.fsoteam.eshop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> resultLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getResultLiveData() {
        return resultLiveData;
    }

    public void signInUser(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        resultLiveData.setValue("signed in successfully");
                    } else {
                        resultLiveData.setValue("Failed to signIn");
                    }
                });
    }
}