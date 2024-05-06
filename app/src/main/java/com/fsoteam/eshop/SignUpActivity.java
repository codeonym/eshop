package com.fsoteam.eshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.fsoteam.eshop.model.User;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText fullName;
    private EditText emailEt;
    private EditText passEt;
    private EditText CpassEt;

    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private ProgressDialog progressDialog;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button signUpBtn = findViewById(R.id.signUpBtn_signUpPage);
        fullName = findViewById(R.id.nameEt_signUpPage);
        emailEt = findViewById(R.id.emailEt_signUpPage);
        passEt = findViewById(R.id.PassEt_signUpPage);
        CpassEt = findViewById(R.id.cPassEt_signUpPage);
        TextView signInTv = findViewById(R.id.signInTv_signUpPage);

        progressDialog = new ProgressDialog(this);

        textAutoCheck();

        signInTv.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        signUpBtn.setOnClickListener(v -> checkInput());
    }

    private void textAutoCheck() {
        fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count >= 4) {
                    fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (fullName.getText().toString().isEmpty()) {
                    fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else if (fullName.getText().length() >= 4) {
                    fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                }
            }
        });

        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (emailEt.getText().toString().matches(emailPattern)) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (emailEt.getText().toString().isEmpty()) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else if (emailEt.getText().toString().matches(emailPattern)) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                }
            }
        });

        passEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 5) {
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (passEt.getText().toString().isEmpty()) {
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else if (passEt.getText().length() > 5) {
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                }
            }
        });

        CpassEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                CpassEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (CpassEt.getText().toString().equals(passEt.getText().toString())) {
                    CpassEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (CpassEt.getText().toString().isEmpty()) {
                    CpassEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else if (CpassEt.getText().toString().equals(passEt.getText().toString())) {
                    CpassEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                }
            }
        });
    }

    private void checkInput() {
        if (fullName.getText().toString().isEmpty()) {
            toast("Name can't empty!");
            return;
        }
        if (emailEt.getText().toString().isEmpty()) {
            toast("Email can't empty!");
            return;
        }

        if (!emailEt.getText().toString().matches(emailPattern)) {
            toast("Enter Valid Email");
            return;
        }
        if (passEt.getText().toString().isEmpty()) {
            toast("Password can't empty!");
            return;
        }
        if (!passEt.getText().toString().equals(CpassEt.getText().toString())) {
            toast("Password not Match");
            return;
        }

        signIn();
    }

    private void signIn() {
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Creating Account");
        progressDialog.show();

        String emailV = emailEt.getText().toString();
        String passV = passEt.getText().toString();
        String fullname = fullName.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(emailV, passV)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        progressDialog.setMessage("Save User Data");

                        User user = new User();
                        user.setUserName(fullname);
                        user.setUserEmail(emailV);
                        user.setUserUid(firebaseAuth.getUid());

                        db.child(DbCollections.USERS).child(firebaseAuth.getUid())
                                .setValue(user)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        progressDialog.dismiss();
                                        toast("Data Saved");
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        progressDialog.dismiss();
                                        toast("Data not Saved");
                                    }
                                });
                    } else {
                        progressDialog.dismiss();
                        toast("failed to Authenticate !");
                    }
                });
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}