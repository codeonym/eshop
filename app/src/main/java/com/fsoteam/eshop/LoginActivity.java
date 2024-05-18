package com.fsoteam.eshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import com.fsoteam.eshop.viewmodel.LoginViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    String signInEmail;
    String signInPassword;
    Button signInBtn;
    EditText emailEt;
    EditText passEt;

    LoadingDialog loadingDialog;

    TextView emailError;
    TextView passwordError;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {

            // User is signed in, redirect to MainActivity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        TextView signUpTv = findViewById(R.id.signUpTv);
        signInBtn = findViewById(R.id.loginBtn);
        emailEt = findViewById(R.id.emailEt);
        passEt = findViewById(R.id.PassEt);
        emailError = findViewById(R.id.emailError);
        passwordError = findViewById(R.id.passwordError);

        textAutoCheck();

        loadingDialog = new LoadingDialog(this);

        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getResultLiveData().observe(this, result -> {
            loadingDialog.dismissDialog();
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            if (result.equals("signed in successfully")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void textAutoCheck() {
        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailEt.getText()).matches()) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                    emailError.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (emailEt.getText().toString().isEmpty()) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else if (Patterns.EMAIL_ADDRESS.matcher(emailEt.getText()).matches()) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                    emailError.setVisibility(View.GONE);
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
                passwordError.setVisibility(View.GONE);
                if (count > 4) {
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (passEt.getText().toString().isEmpty()) {
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else if (passEt.getText().length() > 4) {
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                }
            }
        });
    }

    private void checkInput() {
        if (emailEt.getText().toString().isEmpty()) {
            emailError.setVisibility(View.VISIBLE);
            emailError.setText("Email Can't be Empty");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEt.getText()).matches()) {
            emailError.setVisibility(View.VISIBLE);
            emailError.setText("Enter Valid Email");
            return;
        }
        if (passEt.getText().toString().isEmpty()) {
            passwordError.setVisibility(View.VISIBLE);
            passwordError.setText("Password Can't be Empty");
            return;
        }

        if (!passEt.getText().toString().isEmpty() && !emailEt.getText().toString().isEmpty()) {
            emailError.setVisibility(View.GONE);
            passwordError.setVisibility(View.GONE);
            signInUser();
        }
    }

    private void signInUser() {
        loadingDialog.startLoadingDialog();
        signInEmail = emailEt.getText().toString().trim();
        signInPassword = passEt.getText().toString().trim();
        loginViewModel.signInUser(signInEmail, signInPassword);
    }
}